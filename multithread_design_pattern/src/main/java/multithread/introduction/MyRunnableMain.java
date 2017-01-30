package multithread.introduction;

import java.util.stream.IntStream;

public class MyRunnableMain {
    public static void main(String[] args) {
        Thread myThread = new Thread(new MyRunnable());
        myThread.start();
        IntStream.rangeClosed(1, 10000)
                .forEach(i -> System.out.println("Main"));
    }
}
