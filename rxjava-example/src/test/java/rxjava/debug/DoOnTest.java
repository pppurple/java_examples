package rxjava.debug;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import rxjava.base.DebugSubscriber;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DoOnTest {
    @Test
    public void doOnNextTest() {
        Flowable.range(1, 5)
                .doOnNext(data -> System.out.println("--original: " + data))
                .filter(data -> data % 2 == 0)
                .doOnNext(data -> System.out.println("----after filter: " + data))
                .subscribe(new DebugSubscriber<>());
    }

    @Test
    public void doOnCompleteTest() {
        Flowable.range(1, 5)
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .subscribe(new DebugSubscriber<>());
    }

    @Test
    public void doOnErrorTest() {
        Flowable.range(1, 5)
                .doOnError(error -> System.out.println("original: " + error.getMessage()))
                .map(data -> {
                    if (data == 3) {
                        throw new Exception("exception!!");
                    }
                    return data;
                })
                .doOnError(error -> System.out.println("after map: " + error.getMessage()))
                .subscribe(new DebugSubscriber<>());
    }

    @Test
    public void doOnSubscribeTest() {
        Flowable.range(1, 5)
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe"))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("--Subscriber: onSubscribe");
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println("--Subscriber: onNext: " + data);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // do nothing
                    }

                    @Override
                    public void onComplete() {
                        // do nothing
                    }
                });
    }

    @Test
    public void doOnRequestTest() throws InterruptedException {
        Flowable.range(1, 3)
                .doOnRequest(size -> System.out.println("original: size=" + size))
                .observeOn(Schedulers.computation())
                .doOnRequest(size -> System.out.println("--after observeOn: size=" + size))
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        this.subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println(data);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error: " + throwable);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });
        Thread.sleep(500L);
    }

    @Test
    public void doOnCancelTest() throws InterruptedException {
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .subscribe(new Subscriber<Long>() {
                    private long startTime;
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.startTime = System.currentTimeMillis();
                        this.subscription = subscription;
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long data) {
                        if (System.currentTimeMillis() - startTime > 300L) {
                            System.out.println("cancel");
                            subscription.cancel();
                            return;
                        }

                        System.out.println(data);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error: " + throwable);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });
        Thread.sleep(1_000L);
    }
}
