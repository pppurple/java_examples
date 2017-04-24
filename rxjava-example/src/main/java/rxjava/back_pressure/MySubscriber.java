package rxjava.back_pressure;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber<T> implements Subscriber<T> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("  --> onSubscribe");
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T data) {
        System.out.println("  --> onNext: " + data);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("onError!!!");
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("  --> onComplete");
    }
}
