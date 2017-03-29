package rxjava.operator;

import io.reactivex.Flowable;
import org.junit.Test;
import rxjava.base.DebugSubscriber;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class OperatorTest {
    @Test
    public void justTest() {
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E");

        flowable.subscribe(new DebugSubscriber<String>());
    }

    @Test
    public void fromArrayTest() {
        Flowable<String> flowable = Flowable.fromArray("A", "B", "C", "D", "E");

        flowable.subscribe(new DebugSubscriber<String>());
    }

    @Test
    public void fromCallableTest() {
        Flowable<Long> flowable = Flowable.fromCallable(System::currentTimeMillis);

        flowable.subscribe(new DebugSubscriber<Long>());
    }

    @Test
    public void rangeTest() {
        Flowable<Integer> flowable = Flowable.range(5, 3);

        flowable.subscribe(new DebugSubscriber<Integer>());

        Flowable<Long> flowableLong = Flowable.rangeLong(8L, 3L);

        flowableLong.subscribe(new DebugSubscriber<Long>());
    }

    @Test
    public void intervalTest() throws InterruptedException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");

        System.out.println("start: " + LocalTime.now().format(formatter));

        Flowable<Long> flowable = Flowable.interval(1_000L, TimeUnit.MILLISECONDS);

        flowable.subscribe(data -> {
            System.out.println(Thread.currentThread().getName()
                    + ": " + LocalTime.now().format(formatter)
                    + ": data=" + data);
        });

        Thread.sleep(5_000L);
    }

    @Test
    public void timerTest() throws InterruptedException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");

        System.out.println("start: " + LocalTime.now().format(formatter));

        Flowable<Long> flowable = Flowable.timer(1_000L, TimeUnit.MILLISECONDS);

        flowable.subscribe(data -> {
                    System.out.println(Thread.currentThread().getName()
                            + ": " + LocalTime.now().format(formatter)
                            + ": data=" + data);
                },
                error -> System.out.println("error=" + error),
                () -> System.out.println("complete"));

        Thread.sleep(1_500L);
    }

    @Test
    public void deferTest() throws InterruptedException {
        Flowable<LocalTime> flowable = Flowable.defer(() -> Flowable.just(LocalTime.now()));

        flowable.subscribe(new DebugSubscriber<>("No.1"));

        Thread.sleep(2_000L);

        flowable.subscribe(new DebugSubscriber<>("No.2"));
    }

    @Test
    public void emptyTest() {
        Flowable.empty().subscribe(new DebugSubscriber<>());
    }

    @Test
    public void errorTest() {
        Flowable.error(new Exception("exception!!"))
                .subscribe(new DebugSubscriber<>());
    }

    @Test
    public void neverTest() {
        Flowable.never()
                .subscribe(new DebugSubscriber<>());
    }

    @Test
    public void mapTest() {
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
                .map(String::toLowerCase);

        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void flatMapTest() {
        // flatMap(mapper)
        Flowable<String> flowable = Flowable.just("A", "", "C", "", "E")
                .flatMap(data -> {
                    if ("".equals(data)) {
                        return Flowable.empty();
                    } else {
                        return Flowable.just(data.toLowerCase());
                    }
                });
        flowable.subscribe(new DebugSubscriber<String>());

        // flatMap(mapper, combiner)
        Flowable<String> flowable2 = Flowable.range(1, 3)
                .flatMap(data -> {
                            return Flowable.interval(100L, TimeUnit.MILLISECONDS)
                                    .take(3);
                        },
                        (sourceData, newData) -> "[" + sourceData + "]" + newData);
        flowable2.subscribe(new DebugSubscriber<String>());

        // flatMap(oneNextMapper, onErrorMapper, onCompleteSupplier)
        Flowable<Integer> original = Flowable.just(1, 2, 0, 4, 5)
                .map(data -> 10 / data); // throw exception

        Flowable<Integer> flowable3 = original.flatMap(
                data -> Flowable.just(data),
                error -> Flowable.just(-1),
                () -> Flowable.just(100)
        );
        flowable3.subscribe(new DebugSubscriber<Integer>());
    }

    @Test
    public void concatMapTest() throws InterruptedException {
        // concatMap(mapper)
        Flowable<String> flowable = Flowable.range(10, 3)
                .concatMap(
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                                .take(2)
                                .map(data -> {
                                    long time = System.currentTimeMillis();
                                    return time + "ms: [" + sourceData + "]" + data;
                                }));
        flowable.subscribe(new DebugSubscriber<String>());
        Thread.sleep(4_000L);
    }

    @Test
    public void concatMapEagerTest() throws InterruptedException {
        // concatMapEager(mapper)
        Flowable<String> flowable = Flowable.range(10, 3)
                .concatMapEager(
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                                .take(2)
                                .map(data -> {
                                    long time = System.currentTimeMillis();
                                    return time + "ms: [" + sourceData + "]" + data;
                                }));
        flowable.subscribe(new DebugSubscriber<String>());
        Thread.sleep(4_000L);
    }

    @Test
    public void concatMapEagerDelayErrorTest() throws InterruptedException {
        Flowable<String> flowable = Flowable.range(10, 3)
                .concatMapEagerDelayError(
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                                .take(3)
                                .doOnNext(data -> {
                                    if (sourceData == 11 && data == 1) {
                                        throw new Exception("Exception!!");
                                    }
                                })
                                .map(data -> "[" + sourceData + "]" + data),
                        true);
        flowable.subscribe(new DebugSubscriber<String>());
        Thread.sleep(4_000L);
    }

    @Test
    public void bufferTest() throws InterruptedException {
        // buffer(count)
        Flowable<List<Long>> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(3);
        flowable.subscribe(new DebugSubscriber<List<Long>>());
        Thread.sleep(3_000L);

        // buffer(boundaryIndicatorSupplier)
        Flowable<List<Long>> flowable2 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(7)
                .buffer(() -> Flowable.timer(1_000L, TimeUnit.MILLISECONDS));
        flowable2.subscribe(new DebugSubscriber<List<Long>>());
        Thread.sleep(4_000L);
    }

}