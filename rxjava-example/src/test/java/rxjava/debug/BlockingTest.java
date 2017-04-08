package rxjava.debug;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class BlockingTest {
    @Test
    public void blockingFirstTest() {
        long actual = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .blockingFirst();
        assertThat(actual).isEqualTo(0L);
    }

    @Test
    public void blockingLastTest() {
        long actual = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(3)
                .blockingLast();
        assertThat(actual).isEqualTo(2L);
    }

    @Test
    public void blockingIterableTest() throws InterruptedException {
        Iterable<Long> result = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5)
                .blockingIterable();
        Iterator<Long> iterator = result.iterator();

        assertThat(iterator.hasNext()).isTrue();

        assertThat(iterator.next()).isEqualTo(0L);
        assertThat(iterator.next()).isEqualTo(1L);
        assertThat(iterator.next()).isEqualTo(2L);

        Thread.sleep(1_000L);

        assertThat(iterator.next()).isEqualTo(3L);
        assertThat(iterator.next()).isEqualTo(4L);

        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void blockingSubscribeTest() {
        Flowable<Long> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(5);
        Counter counter = new Counter();

        flowable.blockingSubscribe(new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                counter.increment();
            }

            @Override
            public void onError(Throwable throwable) {
                fail(throwable.getMessage());
            }

            @Override
            public void onComplete() {
                // do nothing
            }
        });

        assertThat(counter.get()).isEqualTo(5);
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