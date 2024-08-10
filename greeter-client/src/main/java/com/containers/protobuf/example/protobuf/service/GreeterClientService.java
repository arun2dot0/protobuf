package com.containers.protobuf.example.protobuf.service;

import com.example.demo.GreeterServiceGrpc;
import com.example.demo.Hello;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GreeterClientService {

    @GrpcClient("greeter-service")
    private GreeterServiceGrpc.GreeterServiceBlockingStub greeterServiceStub;

    public String sendHello(String name) {
        Hello.HelloRequest request = Hello.HelloRequest.newBuilder().setName(name).build();
        Hello.HelloReply response = greeterServiceStub.sendHelloRequest(request);
        return response.getMessage();
    }
}