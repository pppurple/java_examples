package rxjava.basic;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;

public class ObservableMain {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String[] datas = {"Hello World!", "こんにちは、世界"};

                Arrays.stream(datas)
                        .filter(d -> !emitter.isDisposed())
                        .forEach(emitter::onNext);

                emitter.onComplete();
            }
        });

        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        // do nothing
                    }

                    @Override
                    public void onNext(String item) {
                        System.out.println(Thread.currentThread().getName() + ": " + item);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(Thread.currentThread().getName() + ": completed.");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        Thread.sleep(500L);
    }
}
