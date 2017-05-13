package multithread.twophasetermination.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MyTask implements Runnable {
    private final CountDownLatch downLatch;
    private final int context;
    private static final Random random = new Random(321);

    public MyTask(CountDownLatch downLatch, int context) {
        this.downLatch = downLatch;
        this.context = context;
    }

    @Override
    public void run() {
        doTask();
        downLatch.countDown();
    }

    protected void doTask() {
        String name = Thread.currentThread().getName();
        System.out.println(name + ":MyTask:BEGIN:context = " + context);
        try {
            Thread.sleep(random.nextInt(3_000));
        } catch (InterruptedException ignored) {
        } finally {
            System.out.println(name + ":MyTask:END:context = " + context);
        }
    }
}
