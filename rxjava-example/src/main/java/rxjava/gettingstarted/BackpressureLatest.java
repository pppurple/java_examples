package rxjava.gettingstarted;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureLatest {
    public static void main(String[] args) throws InterruptedException {

        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(20)
                .doOnSubscribe(subscription -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("Flowable generated data:" + data))
                .onBackpressureLatest();

        flowable.doOnRequest(req -> System.out.println("<-- request: " + req))
                .observeOn(Schedulers.computation(), false, 2)
                .subscribe(new MySubscriber<>());

        Thread.sleep(11_000L);
    }
}
