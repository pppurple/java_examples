package rxjava.debug;

import io.reactivex.Flowable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingTest {
    @Test
    public void blockingFirstTest() {
        long actual = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .blockingFirst();
        assertThat(actual).isEqualTo(0L);
    }

    @Test
    public void blockingLastTest() {

    }
}