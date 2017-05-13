package multithread.twophasetermination.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MyTask implements Runnable {
    private static final int PHASE = 5;
    private final CyclicBarrier cyclicBarrier;
    private final CountDownLatch doneLatch;
    private final int context;
    private static final Random random = new Random(432143);

    public MyTask(CyclicBarrier cyclicBarrier, CountDownLatch doneLatch, int context) {
        this.cyclicBarrier = cyclicBarrier;
        this.doneLatch = doneLatch;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            for (int phase = 0; phase < PHASE; phase++) {
                doPhase(phase);
                cyclicBarrier.await();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        } finally {
            doneLatch.countDown();
        }
    }

    protected void doPhase(int phase) {
        String name = Thread.currentThread().getName();
        System.out.println(name + ":MyTask:BEGIN:context = " + context + ", phase = " + phase);
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(name + ":MyTask:END:context = " + context + ", phase = " + phase);
        }
    }
}
