package com.containers.protobuf.example.protobuf.service;

import com.example.demo.GreeterServiceGrpc;
import com.example.demo.Hello;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@GrpcService
public class GreeterServiceImpl extends GreeterServiceGrpc.GreeterServiceImplBase {
    Logger log = LoggerFactory.getLogger(GreeterServiceImpl.class);


    @Override
    public StreamObserver<com.example.demo.Hello.HelloRequest> sendHelloRequest(StreamObserver<Hello.HelloReply> responseObserver) {

        return StreamObserverUtility.proxyStream(
                responseObserver,
                this::sayHello
        );
    }
    private Hello.HelloReply sayHello(Hello.HelloRequest request) {
        log.info("Received request: {}", request);
        return Hello.HelloReply
                .newBuilder()
                .setMessage("Hello "
                        + Optional
                        .of(request.getName())
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .orElse("World")
                        + "!"
                )
                .build();
    }

}
