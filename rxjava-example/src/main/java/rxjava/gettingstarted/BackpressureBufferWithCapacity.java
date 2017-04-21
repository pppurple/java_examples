package rxjava.gettingstarted;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class BackpressureBufferWithCapacity {
    public static void main(String[] args) throws InterruptedException {

        Flowable<Long> flowable = Flowable.interval(10L, TimeUnit.MILLISECONDS)
                .take(10)
                .doOnSubscribe(subscription -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("Flowable generated data:" + data))
                .onBackpressureBuffer(3);

        flowable.doOnRequest(req -> System.out.println("<-- request: " + req))
                .observeOn(Schedulers.computation(), false, 2)
                .subscribe(
                        new Subscriber<Long>() {
                            private Subscription subscription;

                            @Override
                            public void onSubscribe(Subscription subscription) {
                                System.out.println("--> onSubscribe");
                                this.subscription = subscription;
                                this.subscription.request(Long.MAX_VALUE);
                            }

                            @Override
                            public void onNext(Long data) {
                                System.out.println("--> onNext: " + data);
                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                throwable.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                System.out.println("--> onComplete");
                            }
                        }
                );

        Thread.sleep(11_000L);
    }
}
