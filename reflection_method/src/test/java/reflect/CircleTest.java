package reflect;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
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
        public void getMethodでMethodオブジェクトが取得できること() throws Exception {
            Method method = Circle.class.getMethod("area");
            assertThat(method.toString(), is("public int reflect.Circle.area()"));
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
                    clazz.getMethod("publicMethod"),
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
                    clazz.getDeclaredMethod("publicMethod"),
                    clazz.getDeclaredMethod("protectedMethod"),
                    clazz.getDeclaredMethod("defaultMethod"),
                    clazz.getDeclaredMethod("privateMethod"),
                    clazz.getDeclaredMethod("toString")
            };
            assertThat(methods, is(arrayContainingInAnyOrder(expected)));
        }
    }
}