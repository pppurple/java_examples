package javase8;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/08/12.
 */
@RunWith(Enclosed.class)
public class MethodReferenceExampleTest {
    public static class ClassMethodReference {
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
            Consumer<String> consumer = str -> ClassMethodReference.printWithBrace(str);
            consumer.accept("abc");
            // メソッド参照
            Consumer<String> consumer2 = ClassMethodReference::printWithBrace;
            consumer2.accept("abc");
        }

        public static void printWithBi(String prefix, String suffix) {
            System.out.println(prefix + ":" + suffix);
        }

        @Test
        public void 複数引数のクラスメソッド参照() throws Exception {
            // ラムダ式
            BiConsumer<String, String> bi = (pre, suf) -> ClassMethodReference.printWithBi(pre, suf);
            bi.accept("aaa", "bbb");
            // メソッド参照
            BiConsumer<String, String> bi2 = ClassMethodReference::printWithBi;
            bi2.accept("aaa", "bbb");
        }
    }

    public static class InstanceMethodReference {
        @Test
        public void インスタンスメソッド参照() throws Exception {
            // ラムダ式
            List<String> list = new ArrayList<>();
            Consumer<String> consumer = str -> list.add(str);
            consumer.accept("abc");
            // インスタンスメソッド参照
            Consumer<String> consumer2 = list::add;
            consumer2.accept("def");

            String actual = list.get(0);
            String expected = "abc";
            assertThat(actual, is(expected));

            String actual2 = list.get(1);
            String expected2 = "def";
            assertThat(actual2, is(expected2));
        }

        @Test
        public void クラス名を指定したインスタンスメソッド参照() throws Exception {
            // ラムダ式
            Function<String, String> func = str -> str.toUpperCase();
            String UpperStr = func.apply("abc");
            // インスタンスメソッド参照
            Function<String, String> func2 = String::toUpperCase;
            String UpperStr2 = func2.apply("abc");

            String expected = "ABC";
            assertThat(UpperStr, is(expected));
            assertThat(UpperStr2, is(expected));
        }
    }

    public static class ConstructorReference {
        @Test
        public void コンストラクタ参照() throws Exception {
            // ラムダ式
            Supplier<Random> supplier = () -> new Random();
            Random rand = supplier.get();
            // コンストラクタ参照
            Supplier<Random> supplier2 = Random::new;
            Random rand2 = supplier2.get();

            rand.setSeed(10L);
            rand2.setSeed(10L);
            int actual = rand.nextInt();
            int actual2 = rand2.nextInt();
            int expected = -1157793070;
            assertThat(actual, is(expected));
            assertThat(actual2, is(expected));
        }

        @Test
        public void ジェネリクスを使用したコンストラクタ参照() {
            // ラムダ式
            Supplier<List<String>> supplier = () -> new ArrayList<>();
            List<String> list = supplier.get();
            // コンストラクタ参照
            Supplier<List<String>> supplier2 = ArrayList<String>::new;
            List<String> list2 = supplier2.get();
        }

        @Test
        public void 引数のあるコンストラクタ参照() {
            // ラムダ式
            Function<Long, Random> func = (seed) -> new Random(seed);
            Random rand = func.apply(10L);
            // コンストラクタ参照
            Function<Long, Random> func2 = Random::new;
            Random rand2 = func2.apply(10L);

            int actual = rand.nextInt();
            int actual2 = rand2.nextInt();
            int expected = -1157793070;
            assertThat(actual, is(expected));
            assertThat(actual2, is(expected));
        }

        @Test
        public void 配列生成() {
            // ラムダ式
            IntFunction<String[]> func = (size) -> new String[size];
            String[] arr = func.apply(5);
            // コンストラクタ参照
            IntFunction<String[]> func2 = String[]::new;
            String[] arr2 = func2.apply(5);

            int length = 5;
            assertThat(arr.length, is(length));
            assertThat(arr2.length, is(length));
        }
    }
}
