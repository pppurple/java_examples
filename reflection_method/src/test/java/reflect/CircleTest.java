package reflect;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by pppurple on 2016/07/18.
 */
@RunWith(Enclosed.class)
public class CircleTest {

    public static class forNameの確認 {
        @Test
        public void Circleクラスのインスタンスが取得できること() throws Exception {
            Class<?> clazz = Class.forName("reflect.Circle");
            Circle circle = (Circle) clazz.newInstance();
            assertThat(circle, is(instanceOf(Circle.class)));
        }
    }

    public static class getMethodの確認 {
        @Test
        public void getMethodでareaメソッドのMethodオブジェクトが取得できること() throws Exception {
            Method method = Circle.class.getMethod("area");
            assertThat(method.toString(), is("public int reflect.Circle.area()"));
        }

        @Test
        public void getMethodで引数ありのareaメソッドのMethodオブジェクトが取得できること() throws Exception {
            Method method = Circle.class.getMethod("area", int.class);
            assertThat(method.toString(), is("public int reflect.Circle.area(int)"));
        }

        @Test
        public void getMethodでpublicメソッドが取得できること() throws Exception {
            Method pub = Circle.class.getMethod("publicMethod");
            assertThat(pub.toString(), is("public int reflect.Circle.publicMethod()"));
        }

        @Test(expected = NoSuchMethodException.class)
        public void getMethodでprotectedメソッドが取得できないこと() throws Exception {
            Method pro = Circle.class.getMethod("protectedMethod");
            assertThat(pro.toString(), is("protected int reflect.Circle.protectedMethod()"));
        }

        @Test(expected = NoSuchMethodException.class)
        public void getMethodでdefaultメソッドが取得できないこと() throws Exception {
            Method def = Circle.class.getMethod("defaultMethod");
            assertThat(def.toString(), is("int reflect.Circle.defaultMethod()"));
        }

        @Test(expected = NoSuchMethodException.class)
        public void getMethodでprivateメソッドが取得できないこと() throws Exception {
            Method pri = Circle.class.getMethod("privateMethod");
            assertThat(pri.toString(), is("private int reflect.Circle.privateMethod()"));
        }

        @Test
        public void getMethodでhashCodeメソッドが取得できること() throws Exception {
            Method method = Circle.class.getMethod("hashCode");
            assertThat(method.toString(), is("public native int java.lang.Object.hashCode()"));
        }
    }

    public static class getMethodsの確認 {
        @Test
        public void getMethodsでCircleクラスとスーパークラスのメソッドが取得できること() throws Exception {
            Class clazz = Circle.class;
            Method[] methods = clazz.getMethods();
            Method[] expected = {
                    clazz.getMethod("area"),
                    clazz.getMethod("area", int.class),
                    clazz.getMethod("publicMethod"),
                    clazz.getMethod("staticMethod"),
                    clazz.getMethod("toString"),
                    clazz.getMethod("wait"),
                    clazz.getMethod("wait", long.class),
                    clazz.getMethod("wait", long.class, int.class),
                    clazz.getMethod("equals", Object.class),
                    clazz.getMethod("hashCode"),
                    clazz.getMethod("getClass"),
                    clazz.getMethod("notify"),
                    clazz.getMethod("notifyAll")
            };
            assertThat(methods, is(arrayContainingInAnyOrder(expected)));
        }
    }

    public static class getDeclaredMethodの確認 {
        @Test
        public void getDeclaredMethodでareaメソッドが取得できること() throws Exception {
            Method method = Circle.class.getDeclaredMethod("area");
            assertThat(method.toString(), is("public int reflect.Circle.area()"));
        }

        @Test
        public void getDeclaredMethodでpublicメソッドが取得できること() throws Exception {
            Method pub = Circle.class.getDeclaredMethod("publicMethod");
            assertThat(pub.toString(), is("public int reflect.Circle.publicMethod()"));
        }

        @Test
        public void getDeclaredMethodでprotectedメソッドが取得できること() throws Exception {
            Method pro = Circle.class.getDeclaredMethod("protectedMethod");
            assertThat(pro.toString(), is("protected int reflect.Circle.protectedMethod()"));
        }

        @Test
        public void getDeclaredMethodでdefaultメソッドが取得できること() throws Exception {
            Method def = Circle.class.getDeclaredMethod("defaultMethod");
            assertThat(def.toString(), is("int reflect.Circle.defaultMethod()"));
        }

        @Test
        public void getDeclaredMethodでprivateメソッドが取得できること() throws Exception {
            Method pri = Circle.class.getDeclaredMethod("privateMethod");
            assertThat(pri.toString(), is("private int reflect.Circle.privateMethod()"));
        }

        @Test
        public void getDeclaredMethodでオーバーライドメソッドが取得できること() throws Exception {
            Method toStr = Circle.class.getDeclaredMethod("toString");
            assertThat(toStr.toString(), is("public java.lang.String reflect.Circle.toString()"));
        }

        @Test(expected = NoSuchMethodException.class)
        public void getDeclaredMethodでhashCodeメソッドが取得できないこと() throws Exception {
            Method method = Circle.class.getDeclaredMethod("hashCode");
            assertThat(method.toString(), is("public native int java.lang.Object.hashCode()"));
        }
    }

    public static class getDeclaredMethodsの確認 {
        @Test
        public void getDeclaredMethodsでCircleクラスのメソッドが取得できること() throws Exception {
            Class clazz = Circle.class;
            Method[] methods = clazz.getDeclaredMethods();
            Method[] expected = {
                    clazz.getDeclaredMethod("area"),
                    clazz.getDeclaredMethod("area", int.class),
                    clazz.getDeclaredMethod("publicMethod"),
                    clazz.getDeclaredMethod("protectedMethod"),
                    clazz.getDeclaredMethod("defaultMethod"),
                    clazz.getDeclaredMethod("privateMethod"),
                    clazz.getDeclaredMethod("staticMethod"),
                    clazz.getDeclaredMethod("toString")
            };
            assertThat(methods, is(arrayContainingInAnyOrder(expected)));
        }
    }

    public static class Methodクラスでメソッド情報取得 {
        @Test
        public void メソッド名を取得() throws Exception {
            Method method = Circle.class.getMethod("area");
            String methodName = method.getName();
            assertThat(methodName, is("area"));
        }

        @Test
        public void メソッドが定義されたクラスを取得() throws Exception {
            Method method = Circle.class.getMethod("area");
            Class clazz = method.getDeclaringClass();
            assertThat(clazz, is(equalTo(Circle.class)));
        }

        @Test
        public void メソッドの引数の型を取得() throws Exception {
            Method method = Circle.class.getMethod("area", int.class);
            Class<?>[] argClazz = method.getParameterTypes();
            assertThat(argClazz, is(arrayContainingInAnyOrder(int.class)));
        }

        @Test
        public void メソッドの戻り値の型を取得() throws Exception {
            Method method = Circle.class.getMethod("area");
            Class retClazz = method.getReturnType();
            assertThat(retClazz, is(equalTo(int.class)));
        }
    }

    public static class invokeでメソッドコール {
        @Test
        public void invokeで引数なしのメソッドを実行() throws Exception {
            Circle circle = Circle.class.newInstance();
            Method method = Circle.class.getMethod("publicMethod");
            int ret = (int) method.invoke(circle);
            assertThat(ret, is(1));
        }

        @Test
        public void invokeで引数ありのメソッドを実行() throws Exception {
            Circle circle = Circle.class.newInstance();
            Method method = Circle.class.getMethod("area", int.class);
            int ret = (int) method.invoke(circle, 3);
            assertThat(ret, is(28));
        }

        @Test
        public void invokeでstaticメソッドを実行() throws Exception {
            Method method = Circle.class.getMethod("staticMethod");
            int ret = (int) method.invoke(nullValue());
            assertThat(ret, is(11));
        }
    }
}