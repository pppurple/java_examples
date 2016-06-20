package junit.randoms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/06/21.
 */
public class RandomMockTest {
    @Test
    public void choiceでAを返す() throws Exception {
        List<String> options = new ArrayList<>();
        options.add("A");
        options.add("B");
        Randoms sut = new Randoms();
        final AtomicBoolean isCallNextIntMethod = new AtomicBoolean();
        sut.generator = new RandomNumberGenerator() {
            @Override
            public int nextInt() {
                isCallNextIntMethod.set(true);
                return 0;
            }
        };
        assertThat(sut.choice(options), is("A"));
        assertThat(isCallNextIntMethod.get(), is(true));
    }
}
