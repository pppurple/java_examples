package rxjava.gettingstarted;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureBufferWithCapacity {
    public static void main(String[] args) throws InterruptedException {

        Flowable<Long> flowable = Flowable.interval(400L, TimeUnit.MILLISECONDS)
                .take(10)
                .doOnSubscribe(subscription -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("Flowable generated data:" + data))
                .onBackpressureBuffer(3, () -> System.out.println("overflow!!!!"));

        flowable.doOnRequest(req -> System.out.println("<-- request: " + req))
                .doOnNext(data -> System.out.println(" -->onNext:" + data))
                .observeOn(Schedulers.computation(), false, 2)
                .doOnRequest(req -> System.out.println("  <-- request: " + req))
                .subscribe(new MySubscriber<>());

        Thread.sleep(11_000L);
    }
}
