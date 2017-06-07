package javase8;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class CompletableFutureTest {
    private static AtomicInteger atomicInt;

    @Before
    public void before() {
        atomicInt = new AtomicInteger();
    }

    @Test
    public void futureTest() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> callable = () -> {
            int generated = atomicInt.incrementAndGet();
            Thread.sleep(3_000L);
            return generated;
        };

        Future<Integer> future = executor.submit(callable);
        int num = future.get();
        int doubled = NumUtil.doubleNum(num);

        assertThat(doubled).isEqualTo(2);
    }

    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> {
            int generated = atomicInt.incrementAndGet();
            try {
                Thread.sleep(3_000L);
            } catch (InterruptedException ignored) {
            }
            return generated;
        };

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(supplier);

        int doubled = completableFuture.thenApply(NumUtil::doubleNum).get();

        assertThat(doubled).isEqualTo(2);
    }

    @Test
    public void thenAcceptTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> {
            int generated = atomicInt.incrementAndGet();
            try {
                Thread.sleep(3_000L);
            } catch (InterruptedException ignored) {
            }
            return generated;
        };

        CompletableFuture.supplyAsync(supplier)
                .thenApply(NumUtil::doubleNum)
                .thenAccept(System.out::println);
    }
    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        FutureTask<Integer> task = new FutureTask<>(() -> {
            int generated = atomicInt.incrementAndGet();
            Thread.sleep(3_000L);
            return generated;
        });

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int generated = atomicInt.getAndIncrement();
                Thread.sleep(3_000L);
                return generated;
            }
        };

        Future<Integer> future1 = executorService.submit(callable);
        Future<Integer> future2 = executorService.submit(callable);
        Future<Integer> future3 = executorService.submit(callable);

        int num1 = future1.get();
        int num2 = future2.get();
        int num3 = future3.get();

        System.out.println(num1 + ":" + num2 + ":" + num3);
    }

    @Test
    public void joinTest() throws ExecutionException, InterruptedException {
        ExecutorService executor= Executors.newFixedThreadPool(3);

        FutureTask<Integer> task = new FutureTask<>(() -> {
            int generated = atomicInt.incrementAndGet();
            Thread.sleep(3_000L);
            return generated;
        });

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int generated = atomicInt.getAndIncrement();
                Thread.sleep(3_000L);
                return generated;
            }
        };

        List<CompletableFuture> futureList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            futureList.add(CompletableFuture.supplyAsync(() -> atomicInt.getAndIncrement(), executor));
        }

        futureList.stream()
                .map(CompletableFuture::join)
                .forEach(System.out::println);
    }

    private static class NumUtil {
        static int doubleNum(int num) {
            return 2 * num;
        }
    }
}
