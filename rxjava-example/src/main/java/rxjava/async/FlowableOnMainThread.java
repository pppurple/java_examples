package rxjava.async;

import io.reactivex.Flowable;
import io.reactivex.subscribers.ResourceSubscriber;

public class FlowableOnMainThread {
    public static void main(String[] args) {
        System.out.println("start");
        Flowable.just(1, 2, 3)
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
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
    }
}
