package com.example.armeria_prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.armeria_prometheus.grpc.Hello.HelloReply;
import com.example.armeria_prometheus.grpc.Hello.HelloRequest;
import com.example.armeria_prometheus.grpc.HelloServiceGrpc.HelloServiceBlockingStub;

import com.linecorp.armeria.client.Clients;

public class RpcClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientApplication.class);

    public static void main(String[] args) {
        HelloServiceBlockingStub helloService = Clients.newClient(
                "gproto+http://127.0.0.1:8083/",
                HelloServiceBlockingStub.class); // or HelloServiceFutureStub.class or HelloServiceStub.class

        HelloRequest request = HelloRequest.newBuilder().setName("Armerian World").build();
        for (int i = 0; i < 100; i++) {
            try {
                HelloReply reply = helloService.hello(request);
                logger.info(reply.getMessage());
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
