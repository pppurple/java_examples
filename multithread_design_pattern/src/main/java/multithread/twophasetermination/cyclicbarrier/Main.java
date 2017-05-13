package multithread.twophasetermination.cyclicbarrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int THREADS = 3;

    public static void main(String[] args) {
        System.out.println("BEGIN");

        ExecutorService service = Executors.newFixedThreadPool(THREADS);

        Runnable barrierAction = () -> System.out.println("Barrier Action!");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREADS, barrierAction);

        CountDownLatch doneLatch = new CountDownLatch(THREADS);

        try {
            for (int i = 0; i < THREADS; i++) {
                service.execute(new MyTask(cyclicBarrier, doneLatch, i));
            }

            System.out.println("AWAIT");
            doneLatch.await();
        } catch (InterruptedException ignored) {
        } finally {
            service.shutdown();
            System.out.println("END");
        }
    }
}
