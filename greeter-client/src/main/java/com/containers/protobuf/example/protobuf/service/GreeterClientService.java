package com.containers.protobuf.example.protobuf.service;


import com.example.demo.GreeterServiceGrpc;
import com.example.demo.Hello;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class GreeterClientService {

    private final ManagedChannel channel;
    private final GreeterServiceGrpc.GreeterServiceStub asyncStub;

    public GreeterClientService( @Value("${grpc.client.greeter-service.address}") String address) {
        String strippedAddress = address.replace("static://", "");
        // Parse the remaining part using URI
        URI uri = null; // Add dummy scheme to parse
        try {
            uri = new URI("dummy://" + strippedAddress);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String host = uri.getHost();
        int port = uri.getPort();
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.asyncStub = GreeterServiceGrpc.newStub(channel);
    }


    public void sendHelloRequests() {
        StreamObserver<Hello.HelloRequest> requestObserver = asyncStub.sendHelloRequest(new StreamObserver<Hello.HelloReply>() {
            @Override
            public void onNext(Hello.HelloReply reply) {
                // Handle each response
                System.out.println("Received: " + reply.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                // Handle errors
                System.err.println("Error receiving response: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Handle stream completion
                System.out.println("Stream completed.");
            }
        });

        try {
            // Send multiple requests
            requestObserver.onNext(Hello.HelloRequest.newBuilder().setName("Gary Fisher").build());
            requestObserver.onNext(Hello.HelloRequest.newBuilder().setName("Danny MacAskill").build());
            requestObserver.onNext(Hello.HelloRequest.newBuilder().setName("Rachel Atherton").build());

            // Mark the end of requests
            requestObserver.onCompleted();
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
    }

    public void shutdown() {
        channel.shutdown();
    }
}