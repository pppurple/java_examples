package multithread.workerthread.with_concurrent;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

public class MainTest {
    @Test
    public void newSingleThreadExecutorTest() throws InterruptedException {
        // newSingleThreadExecutor
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorSupplier supplier = new ExecutorSupplier(executorService);
        try {
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            Thread.sleep(500);
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void newFixedThreadPoolTest() throws InterruptedException {
        // newFixedThreadPool
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorSupplier supplier = new ExecutorSupplier(executorService);
        try {
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            Thread.sleep(500);
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void newCachedThreadPoolTest() throws InterruptedException {
        // newCachedThreadPool
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorSupplier supplier = new ExecutorSupplier(executorService);
        try {
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            Thread.sleep(500);
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void newCachedThreadPoolUsingCacheTest() throws InterruptedException {
        // newCachedThreadPool
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorSupplier supplier = new ExecutorSupplier(executorService);
        try {
            supplier.doSomething();
            Thread.sleep(500);
            supplier.doSomething();
            Thread.sleep(500);
            supplier.doSomething();
            Thread.sleep(500);
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void newSingleThreadScheduledExecutorTest() throws InterruptedException {
        // newSingleThreadScheduledExecutor
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorSupplier supplier = new ScheduledExecutorSupplier(scheduledExecutorService);
        try {
            System.out.println(new Date() + ":start");
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            Thread.sleep(4_000);
        } finally {
            scheduledExecutorService.shutdown();
        }
    }

    @Test
    public void newScheduledThreadPoolTest() throws InterruptedException {
        // newScheduledThreadPool
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ScheduledExecutorSupplier supplier = new ScheduledExecutorSupplier(scheduledExecutorService);
        try {
            System.out.println(new Date() + ":start");
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            Thread.sleep(4_000);
        } finally {
            scheduledExecutorService.shutdown();
        }
    }

    @Test
    public void newWorkStealingPoolTest() throws InterruptedException {
        // newWorkStealingPool
        ExecutorService executorService = Executors.newWorkStealingPool();
        ExecutorSupplier supplier = new ExecutorSupplier(executorService);
        try {
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
            Thread.sleep(500);
        } finally {
            executorService.shutdown();
        }
    }

    private static class ExecutorSupplier {
        private final Executor executor;

        public ExecutorSupplier(Executor executor) {
            this.executor = executor;
        }

        public void doSomething() {
            executor.execute(
                    () -> {
                        // doSomething
                        System.out.println(new Date() + ":" + Thread.currentThread().getName());
                    }
            );
        }
    }

    private static class ExecutorServiceSupplier {
        private final ExecutorService executorService;

        public ExecutorServiceSupplier(ExecutorService executorService) {
            this.executorService = executorService;
        }

        public Future doSomething(Runnable task) {
            return executorService.submit(task::run);
        }
    }

    private static class ScheduledExecutorSupplier {
        private final ScheduledExecutorService scheduledExecutorService;

        public ScheduledExecutorSupplier(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
        }

        public void doSomething() {
            scheduledExecutorService.schedule(
                    () -> {
                        // doSomething
                        System.out.println(new Date() + ":" + Thread.currentThread().getName());
                    },
                    1_000L,
                    TimeUnit.MILLISECONDS
            );
        }
    }
}