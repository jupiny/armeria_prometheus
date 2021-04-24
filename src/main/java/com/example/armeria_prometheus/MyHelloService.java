package com.example.armeria_prometheus;

import com.example.armeria_prometheus.grpc.Hello.HelloReply;
import com.example.armeria_prometheus.grpc.Hello.HelloRequest;
import com.example.armeria_prometheus.grpc.HelloServiceGrpc;

import io.grpc.stub.StreamObserver;

public class MyHelloService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                                     .setMessage("Hello, " + request.getName() + '!')
                                     .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
