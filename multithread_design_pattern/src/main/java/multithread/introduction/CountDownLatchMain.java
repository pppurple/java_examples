package multithread.introduction;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new CountLatchTask(latch));
        }

        try {
            latch.await();
            System.out.println("***await");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("***done await");
            System.out.println("isShutdown: " + executorService.isShutdown());
            System.out.println("isTerminated: " + executorService.isTerminated());

            executorService.shutdown();

            System.out.println("***shutdown");
            System.out.println("isShutdown: " + executorService.isShutdown());
            System.out.println("isTerminated: " + executorService.isTerminated());

            Thread.sleep(100L);

            System.out.println("***teminate");
            System.out.println("isShutdown: " + executorService.isShutdown());
            System.out.println("isTerminated: " + executorService.isTerminated());
        }
    }

    public static class CountLatchTask implements Runnable {
        private final CountDownLatch latch;

        public CountLatchTask(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " : do something");
            latch.countDown();
        }
    }
}
