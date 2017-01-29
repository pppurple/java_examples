package multithread.introduction;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        IntStream.rangeClosed(1, 10000)
                .forEach(i -> System.out.println("Main"));
    }
}
