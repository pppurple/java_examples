package multithread.thread_per_message.with_concurrent;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void threadTest() throws Exception {
        // Thread
        new Thread(MainTest::doSomething).start();
    }

    @Test
    public void runnableTest() {
        // Runnable
        new Thread(new Runnable() {
            @Override
            public void run() {
                doSomething();
            }
        }).start();
    }

    @Test
    public void threadFactoryTest() {
        // ThreadFactory
        ThreadFactory factory = newInstance();
        factory.newThread(new Runnable() {
            @Override
            public void run() {
                doSomething();
            }
        }).start();
    }

    @Test
    public void defaultThreadFactory() {
        // Executors.defaultThreadFactory
        ThreadFactory factory2 = Executors.defaultThreadFactory();
        factory2.newThread(new Runnable() {
            @Override
            public void run() {
                doSomething();
            }
        }).start();
    }

    @Test
    public void executeTest() {
        // Executor.execute
        ExecutorSupplier supplier = new ExecutorSupplier(
                new Executor() {
                    @Override
                    public void execute(Runnable r) {
                        new Thread(r).start();
                    }
                }
        );
        supplier.doSomething();
    }

    @Test
    public void newCachedThreadPoolTest() {
        // ExecutorService
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorSupplier supplier = new ExecutorSupplier(executorService);
        try {
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void newScheduledThreadPool() {
        // ScheduledExecutorService
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        ScheduledExecutorSupplier supplier = new ScheduledExecutorSupplier(scheduledExecutorService);
        try {
            supplier.doSomething();
            supplier.doSomething();
            supplier.doSomething();
        } finally {
            scheduledExecutorService.shutdown();
        }
    }

    private static void doSomething() {
        // do something
        System.out.println(Thread.currentThread().getName() + " start");
    }

    private static ThreadFactory newInstance() {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
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
                    }
            );
        }
    }

    private static class ScheduledExecutorSupplier {
        private final ScheduledExecutorService executorService;

        public ScheduledExecutorSupplier(ScheduledExecutorService executorService) {
            this.executorService = executorService;
        }

        public void doSomething() {
            executorService.schedule(
                    () -> {
                        // doSomething
                    },
                    1_000L,
                    TimeUnit.MILLISECONDS
            );
        }
    }
}