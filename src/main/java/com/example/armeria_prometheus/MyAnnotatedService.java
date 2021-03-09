package com.example.armeria_prometheus;

import java.util.Random;

import javax.inject.Named;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Named
public class MyAnnotatedService {

    private static Random RAND = new Random();
    private Counter counter;

    public MyAnnotatedService(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("api.call.count");
    }

    @Get("/hello")
    public HttpResponse hello() {
        counter.increment();
        int rand = RAND.nextInt(2);
        if (rand % 2 == 0) {
            return HttpResponse.of("world");
        }
        return HttpResponse.ofFailure(new RuntimeException("error"));
    }
}
