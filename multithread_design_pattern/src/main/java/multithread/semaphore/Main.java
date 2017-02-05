package multithread.semaphore;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker(3);

        for (int i = 0; i < 10; i++) {
            new Thread(new User(worker)).start();
        }
    }
}
