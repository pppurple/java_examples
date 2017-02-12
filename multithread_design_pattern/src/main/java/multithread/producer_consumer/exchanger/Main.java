package multithread.producer_consumer.exchanger;

import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        Exchanger<char[]> exchanger = new Exchanger<>();
        char[] buffer1 = new char[10];
        char[] buffer2 = new char[10];
        new Thread(new ProducerTask(exchanger, buffer1, 123456L), "producer").start();
        new Thread(new ConsumerTask(exchanger, buffer2, 234566L), "consumer").start();
    }
}
