package junit.randoms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/06/21.
 */
public class RandomTest {

    @Test
    public void choiceでAを返す() throws Exception {
        List<String> options = new ArrayList<>();
        options.add("A");
        options.add("B");
        Randoms sut = new Randoms();
        sut.generator = new RandomNumberGenerator() {
            @Override
            public int nextInt() {
                return 0;
            }
        };
        assertThat(sut.choice(options), is("A"));
    }

    @Test
    public void choiceでBを返す() throws Exception {
        List<String> options = new ArrayList<>();
        options.add("A");
        options.add("B");
        Randoms sut = new Randoms();
        sut.generator = new RandomNumberGenerator() {
            @Override
            public int nextInt() {
                return 1;
            }
        };
        assertThat(sut.choice(options), is("B"));
    }
}
