package javase8;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/08/07.
 */
@RunWith(Enclosed.class)
public class LambdaExampleTest {
    public static class 匿名クラスとラムダ式 {
        // 匿名クラス
        Comparator<Integer> comparatorAnonymous = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer i2) {
                return Integer.compare(o1, i2);
            }
        };

        // ラムダ式
        Comparator<Integer> comparatorLambda = (Integer o1, Integer i2) -> {
            return Integer.compare(o1, i2);
        };

        @Test
        public void 匿名クラス実行() throws Exception {
            int actual = comparatorAnonymous.compare(5, 6);
            int expected = -1;
            assertThat(actual, is(expected));
        }

        @Test
        public void ラムダ式実行() throws Exception {
            int actual = comparatorLambda.compare(5, 6);
            int expected = -1;
            assertThat(actual, is(expected));
        }
    }

    public static class ラムダ式の省略記法 {
        // ラムダ式
        Comparator<Integer> comparatorLambda = (Integer i1, Integer i2) -> {
            return Integer.compare(i1, i2);
        };

        // 引数の型の省略
        Comparator<Integer> argsTypeLess = (i1, i2) -> {
            return Integer.compare(i1, i2);
        };

        // ブレースの省略
        Comparator<Integer> braceLess = (i1, i2) -> Integer.compare(i1, i2);

        @Test
        public void 引数の型が省略可能であること() throws Exception {
            int actual = comparatorLambda.compare(5, 6);
            int expected = -1;
            assertThat(actual, is(expected));
        }

        @Test
        public void ブレースが省略可能であること() throws Exception {
            int actual = braceLess.compare(5, 6);
            int expected = -1;
            assertThat(actual, is(expected));
        }

        // 文字列を2回繰り返すメソッド
        Function<String, String> doubleString = (String str) -> {
            return str + str;
        };

        // 引数の()を省略
        Function<String, String> parenLess = str -> {
            return str + str;
        };

        // ブレースの省略
        Function<String, String> braceLess2 = str -> str + str;

        @Test
        public void 文字列が2回繰り返されること() throws Exception {
            String actual = doubleString.apply("abc");
            String expected = "abcabc";
            assertThat(actual, is(expected));
        }

        @Test
        public void 引数のカッコが省略可能であること() throws Exception {
            String actual = parenLess.apply("abc");
            String expected = "abcabc";
            assertThat(actual, is(expected));
        }

        @Test
        public void ブレースが省略可能であること2() throws Exception {
            String actual = braceLess2.apply("abc");
            String expected = "abcabc";
            assertThat(actual, is(expected));
        }
    }

    public static class 実質的finalの確認 {
        @Test
        public void 明示的なfinalのローカル変数が参照できること() throws Exception {
            final int numFinal = 10;
            IntUnaryOperator opeFinal = x -> x + numFinal;

            int actual = opeFinal.applyAsInt(5);
            int expected = 15;
            assertThat(actual, is(expected));
        }

        @Test
        public void 実質的finalのローカル変数が参照できること() throws Exception {
            int num = 20;
            IntUnaryOperator ope = x -> x + num;

            int actual = ope.applyAsInt(10);
            int expected = 30;
            assertThat(actual, is(expected));
        }
    }
}
