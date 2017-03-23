package rxjava.operator;

import io.reactivex.Flowable;
import org.junit.Test;
import rxjava.base.DebugSubscriber;

import static org.junit.Assert.*;

public class OperatorTest {
    @Test
    public void fromTest() {
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E");

        flowable.subscribe(new DebugSubscriber<String>());
    }

    @Test
    public void fromArrayTest() {
        Flowable<String> flowable = Flowable.fromArray("A", "B", "C", "D", "E");

        flowable.subscribe(new DebugSubscriber<String>());
    }

    @Test
    public void fromCallableTest() {

    }
}