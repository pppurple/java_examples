package javase8;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class CompletableFutureTest {
    private static AtomicInteger atomicInt;
    private static Random random = new Random();

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
    public void supplyAsyncTest() throws ExecutionException, InterruptedException {
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
        Supplier<Integer> supplier = () -> {
            int generated = atomicInt.incrementAndGet();
            try {
                Thread.sleep(3_000L);
            } catch (InterruptedException ignored) {
            }
            return generated;
        };

        Runnable printTask = () -> System.out.println("task done : " + atomicInt.get());

        CompletableFuture.supplyAsync(supplier)
                .thenRun(printTask);
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

    private CompletableFuture<Integer> first = CompletableFuture
            .supplyAsync(() -> {
                int randomValue = random.nextInt(1_000);
                System.out.println("first : " + randomValue);
                try {
                    Thread.sleep(randomValue);
                } catch (InterruptedException ignored) {
                }
                return randomValue;
            });

    private CompletableFuture<Integer> second = CompletableFuture
            .supplyAsync(() -> {
                int randomValue = random.nextInt(1_000);
                System.out.println("second : " + randomValue);
                try {
                    Thread.sleep(randomValue);
                } catch (InterruptedException ignored) {
                }
                return randomValue;
            });

    private CompletableFuture<Integer> third = CompletableFuture
            .supplyAsync(() -> {
                int randomValue = random.nextInt(1_000);
                System.out.println("third : " + randomValue);
                try {
                    Thread.sleep(randomValue);
                } catch (InterruptedException ignored) {
                }
                return randomValue;
            });

    @Test
    public void applyToEitherTest() throws InterruptedException, ExecutionException {
        int result = first.applyToEither(second, (done) -> {
            System.out.println("done :" + done);
            return done;
        }).get();

        System.out.println("result : " + result);
    }

    @Test
    public void acceptEitherTest() throws ExecutionException, InterruptedException {
        first.acceptEither(second, (done) -> {
            System.out.println("done :" + done);
        });
    }

    @Test
    public void runAfterEitherTest() {
        first.runAfterEither(second, () -> {
            System.out.println("done!");
        });
    }

    @Test
    public void allOfTest() {
        List<CompletableFuture> futureList = Arrays.asList(first, second, third);

        CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[futureList.size()])
        ).join();

        futureList.forEach(done -> {
                    try {
                        System.out.println("done : " + done.get(10, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
        });
    }

    @Test
    public void anyOfTest() {
        List<CompletableFuture> futureList = Arrays.asList(first, second, third);

        CompletableFuture.anyOf(
                futureList.toArray(new CompletableFuture[futureList.size()])
        ).join();

        futureList.forEach(done -> {
            try {
                System.out.println("done : " + done.get(10, TimeUnit.MILLISECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        });
    }

    private static class NumUtil {
        static int doubleNum(int num) {
            return 2 * num;
        }
    }
}
