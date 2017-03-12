package rxjava.async;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class FlowableMerge {
    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        Flowable<Integer> source1 = Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());
        Flowable<Integer> source2 = Flowable.range(1, 10_000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());

        Flowable.merge(source1, source2)
                .subscribe(
                        data -> counter.increment(),
                        error -> System.out.println("error:" + error),
                        () -> System.out.println("counter.get()=" + counter.get())
                );

        Thread.sleep(1_000L);
    }


    public static class Counter {
        private volatile int count;

        void increment() {
            count++;
        }

        int get() {
            return count;
        }
    }
}
