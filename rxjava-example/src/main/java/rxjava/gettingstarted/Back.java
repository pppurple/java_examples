package rxjava.gettingstarted;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Back {
    public static void main(String[] args) throws InterruptedException {

        Flowable<String> flowable2 = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                String[] datas = {"Hello World!", "こんにちは、世界"};

                Arrays.stream(datas)
                        .filter(d -> !emitter.isCancelled())
                        .forEach(emitter::onNext);

                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);
        Flowable<Long> flowable = Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                .take(10)
                .doOnSubscribe(subscription -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("Flowable generated data:" + data))
                .onBackpressureBuffer(3, () -> System.out.println("error!!"));

        flowable.doOnRequest(req -> System.out.println("<-- request: " + req))
                .doOnNext(data -> System.out.println(" -->onNext:" + data))
                .observeOn(Schedulers.computation(), false, 2)
                .doOnRequest(req -> System.out.println("  <-- request: " + req))
                .subscribe(
                        new Subscriber<Long>() {
                            private Subscription subscription;
                            @Override
                            public void onSubscribe(Subscription subscription) {
                                System.out.println("  --> onSubscribe");
                                this.subscription = subscription;
//                                subscription.request(2);
                            }

                            @Override
                            public void onNext(Long data) {
                                System.out.println("  --> onNext: " + data);
                                try {
                                    Thread.sleep(10000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
//                                subscription.request(2);
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

        Thread.sleep(11_000L);
    }
}
