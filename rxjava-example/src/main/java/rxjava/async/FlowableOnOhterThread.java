package rxjava.async;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

public class FlowableOnOhterThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long data) {
                        System.out.println(Thread.currentThread().getName() + ": " + data);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread().getName() + ": complete");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        System.out.println("end");

        Thread.sleep(1_000L);
    }
}
