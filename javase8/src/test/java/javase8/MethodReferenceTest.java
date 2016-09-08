package javase8;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by pppurple on 2016/08/12.
 */
@RunWith(Enclosed.class)
public class MethodReferenceTest {
    public static class ClassMethodReference {

        public static String printWithBrace(String str) {
            return "{" + str + "}";
        }

        @Test
        public void クラスメソッド参照() throws Exception {
            // ラムダ式
            UnaryOperator<String> lambda = str -> ClassMethodReference.printWithBrace(str);
            String lambdaStr = lambda.apply("abc");
            // メソッド参照
            UnaryOperator<String> methodRef = ClassMethodReference::printWithBrace;
            String methodRefStr = methodRef.apply("abc");

            assertThat(lambdaStr).isEqualTo("{abc}");
            assertThat(methodRefStr).isEqualTo("{abc}");
        }

        public static String printWithBi(String prefix, String suffix) {
            return prefix + ":" + suffix;
        }

        @Test
        public void 複数引数のクラスメソッド参照() throws Exception {
            // ラムダ式
            BinaryOperator<String> lambda = (pre, suf) -> ClassMethodReference.printWithBi(pre, suf);
            String lambdaStr = lambda.apply("aaa", "bbb");
            // メソッド参照
            BinaryOperator<String> methodRef = ClassMethodReference::printWithBi;
            String methodRefStr = methodRef.apply("aaa", "bbb");

            assertThat(lambdaStr).isEqualTo("aaa:bbb");
            assertThat(methodRefStr).isEqualTo("aaa:bbb");
        }
    }

    public static class InstanceMethodReference {
        @Test
        public void インスタンスメソッド参照() throws Exception {
            // ラムダ式
            Map<Integer, String> map = new HashMap<>();
            BiFunction<Integer, String, String> lambda = (i, s) -> map.put(i, s);
            lambda.apply(1, "aaa");
            // インスタンスメソッド参照
            BiFunction<Integer, String, String> methodRef = map::put;
            methodRef.apply(2, "bbb");

            String getLambda = map.get(1);
            assertThat(getLambda).isEqualTo("aaa");

            String getMethodRef = map.get(2);
            assertThat(getMethodRef).isEqualTo("bbb");
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
            assertThat(UpperStr).isEqualTo(expected);
            assertThat(UpperStr2).isEqualTo(expected);
        }
    }

    public static class ConstructorReference {
        class Foo {
            Foo() {}
        }

        @Test
        public void コンストラクタ参照() throws Exception {
            // ラムダ式
            Supplier<Foo> lambda = () -> new Foo();
            Foo fooLambda = lambda.get();
            // コンストラクタ参照
            Supplier<Foo> ref = Foo::new;
            Foo fooRef = ref.get();

            assertThat(fooLambda).isInstanceOf(Foo.class);
            assertThat(fooRef).isInstanceOf(Foo.class);
        }

        class Bar {
            String name;
            Bar(String name) {
                this.name = name;
            }
        }

        @Test
        public void 引数のあるコンストラクタ参照() {
            // ラムダ式
            Function<String, Bar> lambda = str -> new Bar(str);
            Bar barLambda = lambda.apply("Lambda Bar");
            // コンストラクタ参照
            Function<String, Bar> ref = Bar::new;
            Bar barRef = ref.apply("Ref Bar");

            assertThat(barLambda.name).isEqualTo("Lambda Bar");
            assertThat(barRef.name).isEqualTo("Ref Bar");
        }

        class Baz<T> {
            T name;
            T getName() {
                return name;
            }
            void setName(T name) {
                this.name = name;
            }
        }

        @Test
        public void ジェネリクスを使用したコンストラクタ参照() throws NoSuchMethodException, NoSuchFieldException {
            // ラムダ式
            Supplier<Baz<String>> lambda = () -> new Baz<>();
            Baz<String> bazLambda = lambda.get();
            // コンストラクタ参照
            Supplier<Baz<String>> ref = Baz::new;
            Baz<String> bazRef = ref.get();

            assertThat(bazLambda).isInstanceOf(Baz.class);
            assertThat(bazRef).isInstanceOf(Baz.class);
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
            assertThat(arr.length).isEqualTo(length);
            assertThat(arr2.length).isEqualTo(length);
        }
    }
}
