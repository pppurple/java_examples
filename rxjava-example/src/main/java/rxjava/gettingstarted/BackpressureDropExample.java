package rxjava.gettingstarted;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BackpressureDropExample {
    public static void main(String[] args) throws InterruptedException {
/*        Flowable<Integer> flowable = Flowable.create(emitter -> {
            IntStream.rangeClosed(1, 10)
                    .forEach(emitter::onNext);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);*/

        Flowable flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(10)
                .onBackpressureDrop();

        flowable.observeOn(Schedulers.computation(), false, 1)
                .subscribe(new MySubscriber(2));

        Thread.sleep(5_000L);
    }
}
