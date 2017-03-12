package rxjava.async;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class FlowableFlatMap {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .flatMap(data -> {
                    return Flowable.just(data)
                            .delay(1_000L, TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            System.out.println(Thread.currentThread().getName() + ": " + data);
        });

        Thread.sleep(2_000L);
    }
}
