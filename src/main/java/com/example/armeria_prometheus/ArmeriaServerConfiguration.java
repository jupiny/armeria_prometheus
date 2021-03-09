package com.example.armeria_prometheus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaServerConfiguration {

    @Bean
    public ArmeriaServerConfigurator serverConfigurator(MyAnnotatedService myService) {
        return server -> server.annotatedService("/", myService);
    }
}
