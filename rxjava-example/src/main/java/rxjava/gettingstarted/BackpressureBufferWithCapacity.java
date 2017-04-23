package rxjava.gettingstarted;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureBufferWithCapacity {
    public static void main(String[] args) throws InterruptedException {

        Flowable.interval(400L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("-->onNext:" + data))
                .take(10)
                .doOnNext(data -> System.out.println("onNext1:" + data))
                .doOnSubscribe(subscription -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("onNext2:" + data))
                .onBackpressureBuffer(3, () -> System.out.println("overflow!!!!"))
                .doOnNext(data -> System.out.println("onNext3:" + data))

                .doOnRequest(req -> System.out.println("<-- request: " + req))
                .doOnNext(data -> System.out.println(" -->onNext:" + data))

                .observeOn(Schedulers.computation(), false, 2)
                .doOnNext(data -> System.out.println("onNext4:" + data))
                .doOnRequest(req -> System.out.println("  <-- request: " + req))
                .subscribe(new MySubscriber<>());

/*        Flowable<Long> flowable = Flowable.interval(400L, TimeUnit.MILLISECONDS)
                .take(10)
                .doOnSubscribe(subscription -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("Flowable generated data:" + data))
                .onBackpressureBuffer(3);

        flowable.doOnRequest(req -> System.out.println("<-- request: " + req))
                .doOnNext(data -> System.out.println(" -->onNext:" + data))
                .observeOn(Schedulers.computation(), false, 2)
                .doOnRequest(req -> System.out.println("  <-- request: " + req))
                .subscribe(new MySubscriber<>());*/

        Thread.sleep(11_000L);
    }
}
