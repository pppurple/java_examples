package rxjava.gettingstarted;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber<T> implements Subscriber<T> {
    private Subscription subscription;
    private long requestNum;

    public MySubscriber(long requestNum) {
        super();
        this.requestNum = requestNum;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");
        this.subscription = subscription;
        System.out.println("**request:" + requestNum);
        this.subscription.request(requestNum);
    }

    @Override
    public void onNext(T data) {
        System.out.println("onNext: " + data);
        try {
            Thread.sleep(1000L);
            System.out.println("AAAAAAAAAAAAA");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("**request:" + requestNum);
//        subscription.request(requestNum);
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
