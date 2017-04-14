package rxjava.gettingstarted;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import java.util.stream.IntStream;

public class BackpressureDropExample {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.create(emitter -> {
            IntStream.rangeClosed(1, 10)
                    .forEach(emitter::onNext);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

    }
}
