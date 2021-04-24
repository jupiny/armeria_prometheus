package com.example.armeria_prometheus;

import java.util.Random;

import com.example.armeria_prometheus.grpc.Hello.HelloReply;
import com.example.armeria_prometheus.grpc.Hello.HelloRequest;
import com.example.armeria_prometheus.grpc.HelloServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class MyHelloService extends HelloServiceGrpc.HelloServiceImplBase {

    private static Random RAND = new Random();

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        int rand = RAND.nextInt(2);
        if (rand % 2 == 0) {
            HelloReply reply = HelloReply.newBuilder()
                                         .setMessage("Hello, " + request.getName() + '!')
                                         .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onError(Status.INTERNAL.asException());
    }
}
