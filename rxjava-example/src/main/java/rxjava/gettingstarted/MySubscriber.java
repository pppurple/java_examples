package rxjava.gettingstarted;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber implements Subscriber {
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
        this.subscription.request(requestNum);
    }

    @Override
    public void onNext(Object data) {
        System.out.println("onNext: " + data);
        subscription.request(requestNum);
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
