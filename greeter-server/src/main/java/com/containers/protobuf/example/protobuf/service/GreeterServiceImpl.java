package com.containers.protobuf.example.protobuf.service;



import com.example.demo.GreeterServiceGrpc;

import com.example.demo.Hello;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class GreeterServiceImpl extends GreeterServiceGrpc.GreeterServiceImplBase {
    Logger log = LoggerFactory.getLogger(GreeterServiceImpl.class);

    @Override
    public void sendHelloRequest(Hello.HelloRequest request, StreamObserver<Hello.HelloReply> responseObserver) {
        log.info("Recieved Message {}",request.getName());
        String message = "Hello, " + request.getName();


        Hello.HelloReply reply = Hello.HelloReply.newBuilder().setMessage(message).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
