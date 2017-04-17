package rxjava.gettingstarted;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BackpressureBufferWithCapacity {
    public static void main(String[] args) throws InterruptedException {
        Flowable flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(10)
                .onBackpressureBuffer(5);

        flowable.observeOn(Schedulers.computation(), false, 2)
                .subscribe(new MySubscriber(2));

        Thread.sleep(11_000L);
    }
}
