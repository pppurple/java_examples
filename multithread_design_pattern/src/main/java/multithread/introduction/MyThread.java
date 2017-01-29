package multithread.introduction;

import java.util.stream.IntStream;

public class MyThread extends Thread {
    public void run() {
        IntStream.rangeClosed(1, 10000)
                .forEach(i -> System.out.println("MyThread"));
    }
}
