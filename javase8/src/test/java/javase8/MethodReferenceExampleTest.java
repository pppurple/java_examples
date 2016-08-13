package javase8;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/08/12.
 */
@RunWith(Enclosed.class)
public class MethodReferenceExampleTest {
    public static class MethodReference {
        @Test
        public void メソッド参照() throws Exception {
            List<String> list = new ArrayList<>();
            list.add("aaa");
            list.add("bbb");
            list.add("ccc");
            // ラムダ式
            list.forEach(str -> System.out.println(str));
            // メソッド参照
            list.forEach(System.out::println);
        }

        public static void printWithBrace(String str) {
            System.out.println("{" + str + "}");
        }

        @Test
        public void クラスメソッド参照() throws Exception {
            // ラムダ式
            Consumer<String> consumer = str -> MethodReference.printWithBrace(str);
            consumer.accept("abc");
            // メソッド参照
            Consumer<String> consumer2 = MethodReference::printWithBrace;
            consumer2.accept("abc");
        }

        public static void printWithBi(String prefix, String suffix) {
            System.out.println(prefix + ":" + suffix);
        }

        @Test
        public void 複数引数のクラスメソッド参照() throws Exception {
            BiConsumer<String, String> bi = (pre, suf) -> MethodReference.printWithBi(pre, suf);
            bi.accept("aaa", "bbb");

            BiConsumer<String, String> bi2 =
        }
    }
}
