package rxjava.back_pressure;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class MissingBackpressure {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(10L, TimeUnit.MILLISECONDS)
                .doOnNext(value -> System.out.println("emit: " + value));

        flowable.observeOn(Schedulers.computation())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long value) {
                        try {
                            System.out.println("waiting.....");
                            Thread.sleep(1_000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("received:" + value);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error=" + throwable);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("end");
                    }
                });

        Thread.sleep(5_000L);
    }
}
