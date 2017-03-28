package rxjava.operator;

import io.reactivex.Flowable;
import org.junit.Test;
import rxjava.base.DebugSubscriber;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    }
}