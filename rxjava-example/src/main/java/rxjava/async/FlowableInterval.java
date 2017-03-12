package rxjava.async;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class FlowableInterval {
    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1_000L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> System.out.println("emit: " + System.currentTimeMillis() + "msec:" + data))
                .subscribe(data -> Thread.sleep(2_000L));

        Thread.sleep(5_000L);
    }
}
