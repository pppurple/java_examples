package javase8;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

        Callable<Integer> callable = () -> atomicInt.incrementAndGet();

        Future<Integer> future = executor.submit(callable);
        int num = future.get();
        int doubled = NumUtil.doubleNum(num);

        assertThat(doubled).isEqualTo(2);
    }

    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> atomicInt.incrementAndGet();

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(supplier);
        int doubled = completableFuture.thenApply(NumUtil::doubleNum).get();

        assertThat(doubled).isEqualTo(2);
    }

    @Test
    public void getTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> atomicInt.incrementAndGet();

        int result = CompletableFuture.supplyAsync(supplier)
                .get();

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void getNowTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> {
            try {
                Thread.sleep(1_000L);
            } catch (InterruptedException ignored) {
            }
            return atomicInt.incrementAndGet();
        };

        int result = CompletableFuture.supplyAsync(supplier)
                .getNow(999);

        assertThat(result).isEqualTo(999);
    }

    @Test
    public void thenApplyTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> atomicInt.incrementAndGet();

        int doubled = CompletableFuture.supplyAsync(supplier)
                .thenApply(NumUtil::doubleNum)
                .get();

        assertThat(doubled).isEqualTo(2);
    }

    @Test
    public void thenAcceptTest() throws ExecutionException, InterruptedException {
        Supplier<Integer> supplier = () -> atomicInt.incrementAndGet();

        CompletableFuture.supplyAsync(supplier)
                .thenAccept(System.out::println);
    }

    @Test
    public void thenRunTest() {
        Runnable printTask = () -> System.out.println("task done : " + atomicInt.get());

        CompletableFuture.supplyAsync(supplier)
                .thenRun(printTask);
    }

    @Test
    public void thenCombineTest() throws ExecutionException, InterruptedException {
        Random random = new Random();

        CompletableFuture<Integer> addNumFuture = CompletableFuture
                .supplyAsync(() -> atomicInt.addAndGet(5));

        CompletableFuture<Integer> randomFuture = CompletableFuture
                .supplyAsync(() -> random.nextInt(100));

        int result = addNumFuture.thenCombine(randomFuture, (add, rand) -> {
            System.out.println("add :" + add + ", random : " + rand);
            return add * rand;
        }).get();

        System.out.println("result : " + result);
    }

    @Test
    public void thenAcceptBothTest() throws ExecutionException, InterruptedException {
        Random random = new Random();

        CompletableFuture<Integer> addNumFuture = CompletableFuture
                .supplyAsync(() -> atomicInt.addAndGet(5));

        CompletableFuture<Integer> randomFuture = CompletableFuture
                .supplyAsync(() -> random.nextInt(100));

        addNumFuture.thenAcceptBoth(randomFuture, (add, rand) -> {
            System.out.println("add :" + add + ", random : " + rand);
            System.out.println("result : " + add + rand);
        });
    }

    @Test
    public void runAfterBothTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> addNumFuture = CompletableFuture
                .supplyAsync(() -> atomicInt.addAndGet(5));

        CompletableFuture<Integer> randomFuture = CompletableFuture
                .supplyAsync(() -> atomicInt.addAndGet(10));

        addNumFuture.runAfterBoth(randomFuture, () -> System.out.println("result : " + atomicInt.get()));
    }

    @Test
    public void handleTest() throws ExecutionException, InterruptedException {
        String[] strings = new String[2];
        strings[0] = "aaa";
        strings[1] = "bbb";

        Supplier<String> supplier = () -> strings[3];

        String result = CompletableFuture.supplyAsync(supplier)
                .handle((t, error) -> {
                    if (error != null) {
                        System.out.println("cause : " + error);
                        return "fallback value";
                    } else {
                        return t;
                    }
                })
                .get();

        assertThat(result).isEqualTo("fallback value");
    }

    @Test
    public void whenCompleteTest() {
        String[] strings = new String[2];
        strings[0] = "aaa";
        strings[1] = "bbb";

        Supplier<String> supplier = () -> strings[3];

        CompletableFuture.supplyAsync(supplier)
                .whenComplete((t, error) -> {
                    if (error != null) {
                        System.out.println("cause : " + error);
                    } else {
                        System.out.println("result : " + t);
                    }
                });
    }

    Supplier<Integer> supplier = () -> {
        int generated = atomicInt.incrementAndGet();
        try {
            Thread.sleep(3_000L);
        } catch (InterruptedException ignored) {
        }
        return generated;
    };

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
