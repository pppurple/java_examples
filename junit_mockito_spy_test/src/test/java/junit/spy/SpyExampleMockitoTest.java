package junit.spy;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

/**
 * Created by pppurple on 2016/06/22.
 */
public class SpyExampleMockitoTest {
    @Test
    public void Mockitoのspyを使ったテスト() throws Exception {
        SpyExample sut = new SpyExample();
        Logger spy = spy(sut.logger);
        final StringBuilder infoLog = new StringBuilder();
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                infoLog.append(invocation.getArguments()[0]);
                invocation.callRealMethod();
                return null;
            }
        }).when(spy).info(anyString());
        sut.logger = spy;
        sut.doSomething();
        assertThat(infoLog.toString(), is("doSomething"));
    }
}
