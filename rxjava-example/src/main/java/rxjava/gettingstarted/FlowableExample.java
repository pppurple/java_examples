package rxjava.gettingstarted;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FlowableExample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Integer> flowable = Flowable.create(emitter -> {
            IntStream.rangeClosed(1, 10)
                    .forEach(emitter::onNext);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        flowable.subscribe(new Subscriber<Integer>() {
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
        });
    }
}
