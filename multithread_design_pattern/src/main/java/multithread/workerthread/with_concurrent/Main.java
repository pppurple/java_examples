package multithread.workerthread.with_concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            new Thread(new ClientThread(executorService), "Alice").start();
            new Thread(new ClientThread(executorService), "Bobby").start();
            new Thread(new ClientThread(executorService), "Cindy").start();
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
