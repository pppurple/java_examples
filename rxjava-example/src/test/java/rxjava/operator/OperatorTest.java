package rxjava.operator;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.Test;
import rxjava.base.DebugMaybeObserver;
import rxjava.base.DebugSingleObserver;
import rxjava.base.DebugSubscriber;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class OperatorTest {
    @Test
    public void justTest() {
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E");

        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void fromArrayTest() {
        Flowable<String> flowable = Flowable.fromArray("A", "B", "C", "D", "E");

        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void fromCallableTest() {
        Flowable<Long> flowable = Flowable.fromCallable(System::currentTimeMillis);

        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void rangeTest() {
        Flowable<Integer> flowable = Flowable.range(5, 3);

        flowable.subscribe(new DebugSubscriber<>());

        Flowable<Long> flowableLong = Flowable.rangeLong(8L, 3L);

        flowableLong.subscribe(new DebugSubscriber<>());
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
        flowable.subscribe(new DebugSubscriber<>());

        // flatMap(mapper, combiner)
        Flowable<String> flowable2 = Flowable.range(1, 3)
                .flatMap(data -> {
                            return Flowable.interval(100L, TimeUnit.MILLISECONDS)
                                    .take(3);
                        },
                        (sourceData, newData) -> "[" + sourceData + "]" + newData);
        flowable2.subscribe(new DebugSubscriber<>());

        // flatMap(oneNextMapper, onErrorMapper, onCompleteSupplier)
        Flowable<Integer> original = Flowable.just(1, 2, 0, 4, 5)
                .map(data -> 10 / data); // throw exception

        Flowable<Integer> flowable3 = original.flatMap(
                data -> Flowable.just(data),
                error -> Flowable.just(-1),
                () -> Flowable.just(100)
        );
        flowable3.subscribe(new DebugSubscriber<>());
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
        flowable.subscribe(new DebugSubscriber<>());
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
        flowable.subscribe(new DebugSubscriber<>());
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
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void bufferTest() throws InterruptedException {
        // buffer(count)
        Flowable<List<Long>> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(3);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);

        // buffer(boundaryIndicatorSupplier)
        Flowable<List<Long>> flowable2 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(7)
                .buffer(() -> Flowable.timer(1_000L, TimeUnit.MILLISECONDS));
        flowable2.subscribe(new DebugSubscriber<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void toListTest() {
        Single<List<String>> single = Flowable.just("A", "B", "C", "D", "E")
                .toList();
        single.subscribe(new DebugSingleObserver<>());
    }

    @Test
    public void toMapTest() {
        // toMap(keySelector)
        Single<Map<Long, String>> single = Flowable.just("1A", "2B", "3C", "1D", "2E")
                .toMap(data -> Long.valueOf(data.substring(0, 1)));
        single.subscribe(new DebugSingleObserver<>());

        // toMap(keySelector, valueSelector)
        Single<Map<Long, String>> single2 = Flowable.just("1A", "2B", "3C", "1D", "2E")
                .toMap(
                        data -> Long.valueOf(data.substring(0, 1)),
                        data -> data.substring(1)
                );
        single2.subscribe(new DebugSingleObserver<>());
    }

    @Test
    public void toMultimapTest() throws InterruptedException {
        // toMultimap(keySelector)
        Single<Map<String, Collection<Long>>> single = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(5)
                .toMultimap(data -> {
                    if (data % 2 == 0) {
                        return "even";
                    } else {
                        return "odd";
                    }
                });
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void filterTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .filter(data -> data % 2 == 0);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void distinctTest() {
        // distinct()
        Flowable<String> flowable = Flowable.just("A", "B", "C", "A", "D", "C")
                .distinct();
        flowable.subscribe(new DebugSubscriber<>());

        // distinct(keySelector)
        Flowable<String> flowable2 = Flowable.just("A", "B", "C", "a", "d", "c")
                .distinct(String::toLowerCase);
        flowable2.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void distinctUntilChangedTest() {
        // distinctUntilChanged()
        Flowable<String> flowable = Flowable.just("A", "a", "a", "A", "a")
                .distinctUntilChanged();
        flowable.subscribe(new DebugSubscriber<>());

        // distinctUntilChanged(comparer)
        Flowable<String> flowable2 = Flowable.just("A", "a", "B", "b", "b")
                .distinctUntilChanged((data1, data2) ->
                        data1.toUpperCase().equals(data2.toUpperCase()));
        flowable2.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void takeTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .take(3);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void takeUntilTest() throws InterruptedException {
        // takeUntil(stopPredicate)
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .takeUntil(data -> data == 3);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);

        // takeUntil(other)
        Flowable<Long> flowable2 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .takeUntil(Flowable.timer(1_000L, TimeUnit.MILLISECONDS));
        flowable2.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void takeWhileTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .takeWhile(data -> data != 3);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void takeLastTest() throws InterruptedException {
        // takeLast(count)
        Flowable<Long> flowable = Flowable.interval(800L, TimeUnit.MILLISECONDS)
                .take(5)
                .takeLast(2);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(5_000L);

        // takeLast(count, time, unit)
        Flowable<Long> flowable2 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(10)
                .takeLast(2, 1_000L, TimeUnit.MILLISECONDS);
        flowable2.subscribe(new DebugSubscriber<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void skipTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .skip(2);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(5_000L);
    }

    @Test
    public void skipUntilTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .skipUntil(Flowable.timer(1_000L, TimeUnit.MILLISECONDS));
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void skipWhileTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .skipWhile(data -> data != 3);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void skipLastTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .take(5)
                .skipLast(2);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(6_000L);
    }

    @Test
    public void throttleFirstTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(10)
                .throttleFirst(1_000L, TimeUnit.MILLISECONDS);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void throttleLastTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(9)
                .throttleLast(1_000L, TimeUnit.MILLISECONDS);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void sampleTest() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(9)
                .sample(1_000L, TimeUnit.MILLISECONDS);
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void throttleWithTimeoutTest() {
        Flowable<String> flowable = Flowable.<String>create(emitter -> {
            emitter.onNext("A");
            Thread.sleep(1_000L);

            emitter.onNext("B");
            Thread.sleep(300L);

            emitter.onNext("C");
            Thread.sleep(300L);

            emitter.onNext("D");
            Thread.sleep(1_000L);

            emitter.onNext("E");
            Thread.sleep(100L);

            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                .throttleWithTimeout(500L, TimeUnit.MILLISECONDS);
        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void debounceTest() {
        Flowable<String> flowable = Flowable.<String>create(emitter -> {
            emitter.onNext("A");
            Thread.sleep(1_000L);

            emitter.onNext("B");
            Thread.sleep(300L);

            emitter.onNext("C");
            Thread.sleep(300L);

            emitter.onNext("D");
            Thread.sleep(1_000L);

            emitter.onNext("E");
            Thread.sleep(100L);

            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                .debounce(500L, TimeUnit.MILLISECONDS);
        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void elementAtTest() throws InterruptedException {
        Maybe<Long> maybe = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .elementAt(3);
        maybe.subscribe(new DebugMaybeObserver<>());
        Thread.sleep(1_000L);
    }

    @Test
    public void mergeTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(2)
                .map(data -> data + 100L);

        Flowable<Long> merged = Flowable.merge(flowable1, flowable2);

        merged.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void concatTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(2)
                .map(data -> data + 100L);

        Flowable<Long> concated = Flowable.concat(flowable1, flowable2);

        concated.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void concatEagerTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(data -> data + 100L);

        List<Flowable<Long>> sources = Arrays.asList(flowable1, flowable2);
        Flowable<Long> concated = Flowable.concatEager(sources);

        concated.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void startWithTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(2)
                .map(data -> data + 100L);

        Flowable<Long> result = flowable1.startWith(flowable2);
        result.subscribe(new DebugSubscriber<>());

        Thread.sleep(3_000L);
    }

    @Test
    public void zipTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3)
                .map(data -> data + 100L);

        Flowable<List<Long>> result = Flowable.zip(
                flowable1,
                flowable2,
                (data1, data2) -> Arrays.asList(data1, data2)
        );

        result.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void combineLatestTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5);

        Flowable<Long> flowable2 = Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3)
                .map(data -> data + 100L);

        Flowable<List<Long>> result = Flowable.combineLatest(
                flowable1,
                flowable2,
                (data1, data2) -> Arrays.asList(data1, data2)
        );

        result.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }

    @Test
    public void isEmptyTest() throws InterruptedException {
        Single<Boolean> single = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .take(3)
                .filter(data -> data >= 3)
                .isEmpty();
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void containsTest() throws InterruptedException {
        Single<Boolean> single = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .contains(3L);
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void allTest() throws InterruptedException {
        Single<Boolean> single = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .take(3)
                .all(data -> data < 5);
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void sequenceEqualTest() throws InterruptedException {
        Flowable<Long> flowable1 = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .take(3);
        Flowable<Long> flowable2 = Flowable.just(0L, 1L, 2L);

        Single<Boolean> single = Flowable.sequenceEqual(flowable1, flowable2);
        single.subscribe(new DebugSingleObserver<>());

        Thread.sleep(4_000L);
    }

    @Test
    public void countTest() throws InterruptedException {
        Single<Long> single = Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .take(3)
                .count();
        single.subscribe(new DebugSingleObserver<>());
        Thread.sleep(4_000L);
    }

    @Test
    public void reduceTest() {
        Single<Integer> single = Flowable.just(1, 10, 100, 1_000, 10_000)
                .reduce(0, (sum, data) -> sum + data);
        single.subscribe(new DebugSingleObserver<>());
    }

    @Test
    public void scanTest() {
        Flowable<Integer> flowable = Flowable.just(1, 10, 100, 1_000, 10_000)
                .scan(0, (sum, data) -> sum + data);
        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void repeatTest() {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .repeat(2);
        flowable.subscribe(new DebugSubscriber<>());
    }

    @Test
    public void repeatUntilTest() throws InterruptedException {
        final long startTime = System.currentTimeMillis();

        Flowable<Long> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(3)
                .repeatUntil(() -> {
                    System.out.println("called");
                    return System.currentTimeMillis() - startTime > 500L;
                });
        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(1_000L);
    }

    @Test
    public void repeatWhenTest() throws InterruptedException {
        Flowable<String> flowable = Flowable.just(1, 2, 3)
                .repeatWhen(completeHandler -> {
                    return completeHandler.delay(1_000L, TimeUnit.MILLISECONDS)
                            .take(2)
                            .doOnNext(data -> System.out.println("emit: " + data))
                            .doOnComplete(() -> System.out.println("complete"));
                })
                .map(data -> {
                    long time = System.currentTimeMillis();
                    return time + "ms: " + data;
                });

        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(5_000L);

        Flowable<String> flowable2 = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(3)
                .repeatWhen(completeHandler -> {
                    return completeHandler.delay(1_000L, TimeUnit.MILLISECONDS)
                            .take(2)
                            .doOnNext(data -> System.out.println("emit: " + data))
                            .doOnComplete(() -> System.out.println("complete"));
                })
                .map(data -> {
                    long time = System.currentTimeMillis();
                    return time + "ms: " + data;
                });

        flowable2.subscribe(new DebugSubscriber<>());
        Thread.sleep(5_000L);
    }

    @Test
    public void delayTest() throws InterruptedException {
        System.out.println("start: " + System.currentTimeMillis());

        Flowable<String> flowable = Flowable.<String>create(emitter -> {
            System.out.println("subscribe start: " + System.currentTimeMillis());
            emitter.onNext("A");
            emitter.onNext("B");
            emitter.onNext("C");
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                .delay(2_000L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("time: " + System.currentTimeMillis()));

        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void delaySubscriptionTest() throws InterruptedException {
        System.out.println("start: " + System.currentTimeMillis());

        Flowable<String> flowable = Flowable.<String>create(emitter -> {
            System.out.println("subscribe start: " + System.currentTimeMillis());
            emitter.onNext("A");
            emitter.onNext("B");
            emitter.onNext("C");
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                .delaySubscription(2_000L, TimeUnit.MILLISECONDS);

        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(3_000L);
    }

    @Test
    public void timeoutTest() throws InterruptedException {
        Flowable<Integer> flowable = Flowable.<Integer>create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);

            Thread.sleep(1_200L);

            emitter.onNext(3);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
                .timeout(1_000L, TimeUnit.MILLISECONDS);

        flowable.subscribe(new DebugSubscriber<>());
        Thread.sleep(2_000L);
    }
}
