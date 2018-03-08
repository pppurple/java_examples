package com.example.grpc;

import com.example.grpc.Person.Country;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MyServiceServer {
    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new PeopleServiceImpl())
                .build()
                .start();

        System.out.println("Server started... (port=" + port + ")");

        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.err.println("*** shutting down gRPC server since JVM is shutting down");
                    MyServiceServer.this.stop();
                    System.err.println("*** server shut down");
                }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final MyServiceServer server = new MyServiceServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class PeopleServiceImpl extends PeopleServiceGrpc.PeopleServiceImplBase {
        @Override
        public void searchByName(SearchRequest request, StreamObserver<SearchResponse> responseObserver) {

            System.out.println("query=" + request.getQuery());

            // DBから検索していると想定
            // search from DB
            Person alice = Person.newBuilder().setName("Alice Wall")
                    .setAge(20)
                    .setCountry(Country.JAPAN)
                    .setHobby("tennis")
                    .build();
            Person bobby = Person.newBuilder().setName("Bobby Wall")
                    .setAge(33)
                    .setCountry(Country.CANADA)
                    .setHobby("music")
                    .build();
             List<Person> people = Arrays.asList(alice, bobby); // 検索結果

            SearchResponse response = SearchResponse.newBuilder()
                    .addAllPeople(people)
                    .build();
            /*
            SearchResponse response = SearchResponse.newBuilder()
                    .addPeople(alice)
                    .addPeople(bobby)
                    .build();
            */
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
