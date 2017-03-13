package rxjava.error_handling;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Retry {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.<Integer> create(emitter -> {
            System.out.println("start Flowable");
            for (int i = 1; i <= 3; i++) {
                if (i == 2) {
                    throw new Exception("exception!");
                }
                emitter.onNext(i);
            }

            emitter.onComplete();
            System.out.println("end Flowable");
        }, BackpressureStrategy.BUFFER)
                .doOnSubscribe(subscription -> System.out.println("flowable: doOnSubscribe"))
                .retry(2);

        flowable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("subscriber: onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println("data=" + data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error=" + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        });
    }
}
