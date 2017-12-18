package com.example.thrift;

import com.example.thrift.PeopleService.Processor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.util.Arrays;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            Processor<PeopleService.Iface> processor = new Processor<>(new PeopleService.Iface() {
                @Override
                public List<Person> searchByName(String query) {

                    System.out.println("query: " + query);

                    // dummy response.(emulate search by query from DB)
                    Person alice = new Person("Alice Wall", 33, Country.findByValue(1), "trick");
                    Person bobby = new Person("Bobby Wall", 29, Country.findByValue(0), "yo-yo");
                    List<Person> people = Arrays.asList(alice, bobby);

                    people.forEach(System.out::println);

                    return people;
                }
            });

            // written with lambda
            /*
            Processor<PeopleService.Iface> processor = new Processor<>(query -> {

                Person alice = new Person("Alice", 33, Country.findByValue(1), "trick");
                Person bobby = new Person("Bobby", 29, Country.findByValue(0), "yo-yo");
                List<Person> people = Arrays.asList(alice, bobby);

                return people;
            });
            */

            TServerTransport serverTransport = new TServerSocket(8080);

            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the server... ");

            new Thread(server::serve).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
