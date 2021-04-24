package com.example.armeria_prometheus;

import java.nio.charset.StandardCharsets;

import com.example.armeria_prometheus.grpc.Hello.HelloReply;
import com.example.armeria_prometheus.grpc.Hello.HelloRequest;
import com.example.armeria_prometheus.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

public class HttpClientApplication {

    public static void main(String[] args) {
        WebClient client = WebClient.of("http://localhost:8083/");
        for (int i = 0; i < 100; i++) {
            AggregatedHttpResponse res = client.get("/hello").aggregate().join();
            System.out.println(res.content().toString(StandardCharsets.UTF_8));
        }
    }
}
