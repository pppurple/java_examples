package rxjava.async;

import io.reactivex.Flowable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class FlowableConcatMapEager {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .concatMapEager(data -> {
                    return Flowable.just(data)
                            .delay(1_000L, TimeUnit.MILLISECONDS);
                });

        flowable.subscribe(data -> {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(Thread.currentThread().getName() + ": data=" + data + ", time=" + time);
        });

        Thread.sleep(2_000L);
    }
}
