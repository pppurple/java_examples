package rxjava.gettingstarted;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Integer> flowable = Flowable.create(flowableEmitter -> {
            IntStream.rangeClosed(1, 5)
                    .forEach(flowableEmitter::onNext);
            flowableEmitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        flowable.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer num) {
                System.out.println("onNext: " + num);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
