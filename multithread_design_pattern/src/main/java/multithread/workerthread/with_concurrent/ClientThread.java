package multithread.workerthread.with_concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class ClientThread implements Runnable {
    private final ExecutorService executorService;
    private static final Random random = new Random();

    public ClientThread(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Request request = new Request(Thread.currentThread().getName(), i);
                executorService.execute(request);
                Thread.sleep(1_000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RejectedExecutionException e) {
            System.out.println(Thread.currentThread().getName() + " : " + e);
        }
    }
}
