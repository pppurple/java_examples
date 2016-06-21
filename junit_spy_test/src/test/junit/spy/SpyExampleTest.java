package junit.spy;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/06/21.
 */
public class SpyExampleTest {
    @Test
    public void SpyLoggerを利用したテスト() {
        SpyExample sut = new SpyExample();
        SpyLogger spy = new SpyLogger(sut.logger);
        sut.logger = spy;
        sut.doSomething();
        assertThat(spy.log.toString(), is("doSomething"));
    }
}
