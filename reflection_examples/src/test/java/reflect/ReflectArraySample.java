package reflect;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.*;

/**
 * Created by pppurple on 2016/07/26.
 */
public class ReflectArraySample {
    @Test
    public void Arrayクラスでの配列生成() throws Exception {
        Object array = Array.newInstance(String.class, 3);
        assertThat(array, is(instanceOf(String[].class)));
    }

    @Test
    public void Arrayクラスでの配列へのセット確認() throws Exception {
        Object array = Array.newInstance(String.class, 3);
        Array.set(array, 0, "one");
        Array.set(array, 1, "two");
        Array.set(array, 2, "three");
        String[] strings = String[].class.cast(array);
        String[] expected = {"one", "two", "three"};

        assertThat(strings, is(arrayContaining(expected)));
    }
}
