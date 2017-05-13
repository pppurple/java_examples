package multithread.twophasetermination.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int TASKS = 10;

    public static void main(String[] args) {
        System.out.println("BEGIN");
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch downLatch = new CountDownLatch(TASKS);

        try {
            for (int t = 0; t < TASKS; t++) {
                service.execute(new MyTask(downLatch, t));
            }
            System.out.println("AWAIT");
            downLatch.await();
        } catch (InterruptedException ignored) {
        } finally {
            service.shutdown();
            System.out.println("END");
        }
    }
}
