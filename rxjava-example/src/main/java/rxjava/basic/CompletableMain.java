package rxjava.basic;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CompletableMain {
    public static void main(String[] args) throws InterruptedException {
        Completable completable = Completable.create(emitter -> {
            System.out.println("emit!");
            emitter.onComplete();
        });

        completable.subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        // do nothing
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        Thread.sleep(1_000L);
    }
}
