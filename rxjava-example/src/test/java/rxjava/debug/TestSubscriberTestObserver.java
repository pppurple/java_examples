package rxjava.debug;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestSubscriberTestObserver {
    @Test
    public void testSubscriberTest() throws InterruptedException {
        Flowable<Long> target = Flowable.interval(100L, TimeUnit.MILLISECONDS);

        TestSubscriber<Long> testSubscriber = target.test();

        testSubscriber.assertEmpty();

        testSubscriber.await(150L, TimeUnit.MILLISECONDS);

        testSubscriber.assertValues(0L);

        testSubscriber.await(100L, TimeUnit.MILLISECONDS);

        testSubscriber.assertValues(0L, 1L);
    }
}