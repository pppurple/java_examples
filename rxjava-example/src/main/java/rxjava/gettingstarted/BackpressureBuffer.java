package rxjava.gettingstarted;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BackpressureBuffer {
    public static void main(String[] args) throws InterruptedException {
/*        Flowable<Integer> flowable = Flowable.create(emitter -> {
            IntStream.rangeClosed(1, 10)
                    .forEach(emitter::onNext);
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            for (int i : list) {
                emitter.onNext(i);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);*/

        Flowable<Long> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(10)
                .doOnNext(System.out::println)
                .onBackpressureBuffer();

        flowable.doOnRequest(req -> System.out.println("request before:" + req))
                .observeOn(Schedulers.computation(), false, 2)
                .doOnRequest(req -> System.out.println("request after:" + req))
                .subscribe(
                        new Subscriber<Long>() {
                            private Subscription subscription;

                            @Override
                            public void onSubscribe(Subscription subscription) {
                                System.out.println("onSubscribe");
                                this.subscription = subscription;
                                System.out.println("**request:" + Long.MAX_VALUE);
                                this.subscription.request(Long.MAX_VALUE);
                            }

                            @Override
                            public void onNext(Long data) {
                                System.out.println("onNext: " + data);
                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
//                                this.subscription.request(2);
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
/*        flowable.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                this.subscription = subscription;
                this.subscription.request(2);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println("onNext: " + data);
                subscription.request(2);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });*/

        Thread.sleep(10_000L);
    }
}
