package com.containers.protobuf.example.protobuf.service;

import io.grpc.stub.StreamObserver;

import java.util.function.Function;

final class StreamObserverUtility {

    private StreamObserverUtility() {
    }

    static <Target, Source> StreamObserver<Source> proxyStream(
            StreamObserver<Target> target,
            Function<Source, Target> handler
    ) {
        return new StreamObserver<Source>() {
            @Override
            public void onNext(Source value) {
                final Target targetValue = handler.apply(value);
                target.onNext(targetValue);
            }

            @Override
            public void onError(Throwable t) {
                target.onError(t);
            }

            @Override
            public void onCompleted() {
                target.onCompleted();
            }
        };
    }

}
