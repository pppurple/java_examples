package com.example.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;

public class Client {
    public static void main(String [] args) {

        try {
            TTransport transport = new TSocket("localhost", 8080);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            PeopleService.Client client = new PeopleService.Client(protocol);

            perform(client);

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(PeopleService.Client client) throws TException
    {
        List<Person> people = client.searchByName("Wall");
        people.forEach(System.out::println);
    }
}
