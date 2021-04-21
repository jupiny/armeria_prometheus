package com.example.armeria_prometheus;

import java.util.concurrent.CompletableFuture;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@SpringBootApplication
public class ArmeriaPrometheusApplication {

    public static void main(String[] args) {
        PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        Server server = Server
                .builder()
                .http(8083)
                .meterRegistry(meterRegistry)
                .annotatedService(new MyAnnotatedService(meterRegistry))
                .decorator(MetricCollectingService.newDecorator(MeterIdPrefixFunction.ofDefault("my.server")))
                .service("/metrics", new PrometheusExpositionService(meterRegistry.getPrometheusRegistry()))
                .build();

        CompletableFuture<Void> future = server.start();
        future.join();
    }
}
