package rxjava.debug;

import io.reactivex.Flowable;
import io.reactivex.schedulers.*;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TestSchedulerTestForDebug {
    @Test
    public void testSchedulerTest() {
        long start = System.currentTimeMillis();

        TestScheduler testScheduler = new TestScheduler();

        Flowable<Long> flowable = Flowable.interval(500L, TimeUnit.MILLISECONDS, testScheduler);

        TestSubscriber<Long> result = flowable.test();

        System.out.println("data=" + result.values());
        result.assertEmpty();

        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);

        System.out.println("data=" + result.values());
        result.assertValues(0L);

        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);

        System.out.println("data=" + result.values());
        result.assertValues(0L, 1L);

        testScheduler.advanceTimeTo(2_000L, TimeUnit.MILLISECONDS);

        System.out.println("data=" + result.values());
        result.assertValues(0L, 1L, 2L, 3L);

        System.out.println("testScheduler#now=" + testScheduler.now(TimeUnit.MILLISECONDS));

        long totalTime = System.currentTimeMillis() - start;
        System.out.println("total time=" + totalTime);
    }

}