package rxjava.async;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

public class ObserveOnDROP {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                // バッファを超えたら破棄するback pressure
                .onBackpressureDrop();

        // 非同期でバッファサイズ1
        flowable.observeOn(Schedulers.computation(), false, 1)
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long item) {
                        try {
                            // dummy heavy task
                            Thread.sleep(1_000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                        System.out.println(Thread.currentThread().getName() + ": " + item);
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

        Thread.sleep(7_000L);
    }
}
