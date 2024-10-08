package com.containers.protobuf.example.protobuf.controller;


import com.containers.protobuf.example.protobuf.service.GreeterClientService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String testPing() {
        return "Its Working!";
    }


   @Autowired
   GreeterClientService greeterClientService;



    @GetMapping("/hello")
    public String testGrpc(@RequestParam String name) {
        return greeterClientService.sendHello(name);

    }
}
