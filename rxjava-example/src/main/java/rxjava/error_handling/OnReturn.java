package rxjava.error_handling;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public class OnReturn {
    public static void main(String[] args) {
        Flowable.just(1, 3, 5, 0, 2, 4)
                .map(data -> 100 / data)
                .onErrorReturnItem(0)
                .subscribe(new DisposableSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        System.out.println("data=" + data);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error=" + throwable);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });
    }
}
