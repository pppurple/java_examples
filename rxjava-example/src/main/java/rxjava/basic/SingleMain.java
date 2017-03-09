package rxjava.basic;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class SingleMain {
    public static void main(String[] args) {
        Single<DayOfWeek> single = Single.create(emitter -> {
            emitter.onSuccess(LocalDate.now().getDayOfWeek());
        });

        single.subscribe(new SingleObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                // do nothing
            }

            @Override
            public void onSuccess(DayOfWeek dayOfWeek) {
                System.out.println("onSuccess: " + dayOfWeek);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
