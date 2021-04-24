package com.example.armeria_prometheus;

import com.example.armeria_prometheus.grpc.Hello.HelloReply;
import com.example.armeria_prometheus.grpc.Hello.HelloRequest;
import com.example.armeria_prometheus.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import com.linecorp.armeria.client.Clients;

public class RpcClientApplication {

    public static void main(String[] args) {
        HelloServiceBlockingStub helloService = Clients.newClient(
                "gproto+http://127.0.0.1:8083/",
                HelloServiceBlockingStub.class); // or HelloServiceFutureStub.class or HelloServiceStub.class

        HelloRequest request = HelloRequest.newBuilder().setName("Armerian World").build();
        HelloReply reply = helloService.hello(request);
        assert reply.getMessage().equals("Hello, Armerian World!");
    }
}
