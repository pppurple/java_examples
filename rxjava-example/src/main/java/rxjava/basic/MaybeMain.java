package rxjava.basic;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MaybeMain {
    public static void main(String[] args) {
        Maybe<DayOfWeek> maybe = Maybe.create(emitter -> {
            emitter.onSuccess(LocalDate.now().getDayOfWeek());
        });

        maybe.subscribe(new MaybeObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                // do nothing
            }

            @Override
            public void onSuccess(DayOfWeek dayOfWeek) {
                System.out.println("onSuccess: " + dayOfWeek);
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
    }
}
