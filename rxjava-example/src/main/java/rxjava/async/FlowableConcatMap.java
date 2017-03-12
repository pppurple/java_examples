package rxjava.async;

import io.reactivex.Flowable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class FlowableConcatMap {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .concatMap(data -> {
                    return Flowable.just(data)
                            .delay(1_000L, TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(Thread.currentThread().getName() + ": data=" + data + ", time=" + time);
        });

        Thread.sleep(4_000L);
    }
}
