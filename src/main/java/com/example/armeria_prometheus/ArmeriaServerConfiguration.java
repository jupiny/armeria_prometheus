package com.example.armeria_prometheus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import io.micrometer.prometheus.PrometheusMeterRegistry;

@Configuration
public class ArmeriaServerConfiguration {

    @Bean
    public ArmeriaServerConfigurator prometheusConfigurator(PrometheusMeterRegistry registry) {
        return server -> server
                .service("/metrics",
                         new PrometheusExpositionService(registry.getPrometheusRegistry()));
    }

    @Bean
    public ArmeriaServerConfigurator serverConfigurator(MyAnnotatedService myService) {
        return server -> server
                .annotatedService("/", myService);
    }

    @Bean
    public MeterIdPrefixFunction meterIdPrefixFunction() {
        return MeterIdPrefixFunction.ofDefault("my.server");
    }

    @Bean
    public MeterFilter meterFilter() {
        return new MeterFilter() {
            @Override
            public MeterFilterReply accept(Id id) {
                return id.getName().startsWith("my.server") ?
                       MeterFilterReply.ACCEPT : MeterFilterReply.DENY;
            }
        };
    }
}
