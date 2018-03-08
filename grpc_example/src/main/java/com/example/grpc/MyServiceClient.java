package com.example.grpc;

import com.example.grpc.PeopleServiceGrpc.PeopleServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

public class MyServiceClient {
    private final ManagedChannel channel;
    private final PeopleServiceBlockingStub blockingStub;

    private MyServiceClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build()
        );
    }

    private MyServiceClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = PeopleServiceGrpc.newBlockingStub(channel);
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private SearchResponse searchByName(String query) {
        SearchRequest request = SearchRequest.newBuilder()
                .setQuery(query)
                .build();
        SearchResponse response = null;

        try {
            response = blockingStub.searchByName(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed. " + e);
        }

        return response;
    }

    public static void main(String[] args) throws InterruptedException {
        MyServiceClient client = new MyServiceClient("localhost", 50051);

        SearchResponse response;

        try {
            response = client.searchByName("Wall");
        } finally {
            client.shutdown();
        }

        // 結果を出力
        response.getPeopleList()
                .forEach(p -> {
                    System.out.println("[response]");
                    System.out.println(p);
                });
    }
}
