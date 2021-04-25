package com.example.armeria_prometheus;

import java.util.concurrent.CompletableFuture;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.armeria.common.grpc.GrpcMeterIdPrefixFunction;
import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;

import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@SpringBootApplication
public class ArmeriaPrometheusApplication {

    public static void main(String[] args) {
        PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        meterRegistry.config()
                     .meterFilter(MeterFilter.denyNameStartsWith("armeria"));
        Server server = Server
                .builder()
                .http(8083)
                .meterRegistry(meterRegistry)
                .annotatedService(new MyAnnotatedService(meterRegistry),
                                  MetricCollectingService
                                          .builder(MeterIdPrefixFunction.ofDefault("my.server")
                                                                        .andThen(new MyMeterIdPrefixFunction()))
                                          .successFunction((context, log) -> {
                                              final int statusCode = log.responseHeaders().status().code();
                                              return (statusCode >= 200 && statusCode < 400) || statusCode == 404;
                                          })
                                          .newDecorator())
                .service(GrpcService.builder()
                                    .addService(new MyHelloService())
                                    .build(),
                         MetricCollectingService.newDecorator(GrpcMeterIdPrefixFunction.of("my.grpc")))
                .service("/metrics", PrometheusExpositionService.of(meterRegistry.getPrometheusRegistry()))
                .build();

        CompletableFuture<Void> future = server.start();
        future.join();
    }
}
