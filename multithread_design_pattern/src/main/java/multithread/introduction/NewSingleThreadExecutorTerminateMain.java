package multithread.introduction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewSingleThreadExecutorTerminateMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new CountTask(4));

        System.out.println("isShutdown: " + executorService.isShutdown());
        System.out.println("isTerminated: " + executorService.isTerminated());

        Thread.sleep(1_000L);
        executorService.shutdown();

        System.out.println("isShutdown: " + executorService.isShutdown());
        System.out.println("isTerminated: " + executorService.isTerminated());

        Thread.sleep(1_000L);

        System.out.println("isShutdown: " + executorService.isShutdown());
        System.out.println("isTerminated: " + executorService.isTerminated());
    }
}
