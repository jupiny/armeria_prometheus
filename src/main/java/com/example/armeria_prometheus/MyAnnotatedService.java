package com.example.armeria_prometheus;

import java.util.Random;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class MyAnnotatedService {

    private static Random RAND = new Random();
    private Counter counter;

    public MyAnnotatedService(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("api.call.count");
    }

    @Get("/hello/:msg")
    public HttpResponse hello(@Param("msg") String msg) {
        counter.increment();
        int rand = RAND.nextInt(1000);
        if (rand % 3 == 0) {
            return HttpResponse.of(HttpStatus.NOT_FOUND);
        }
        if (rand % 2 == 0) {
            return HttpResponse.of(msg);
        }
        return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
