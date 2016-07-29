package reflect;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by pppurple on 2016/07/26.
 */
@RunWith(Enclosed.class)
public class ReflectConstructorExampleTest {
    public static class getConstructorの確認 {
        @Test
        public void getConstructorでpublicコンストラクタが取得できること() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getConstructor();
            int mod = cons.getModifiers();
            assertThat(mod, is(Modifier.PUBLIC));
        }

        @Test(expected = NoSuchMethodException.class)
        public void getConstructorでprotectedコンストラクタが取得できないこと() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getConstructor(String.class);
        }

        @Test(expected = NoSuchMethodException.class)
        public void getConstructorでdefaultコンストラクタが取得できないこと() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getConstructor(String.class, String.class);
        }

        @Test(expected = NoSuchMethodException.class)
        public void getConstructorでprivateコンストラクタが取得できないこと() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getConstructor(int.class);
        }

        @Test
        public void getDeclaringClass() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getConstructor();
            Class clazz = cons.getDeclaringClass();
            assertThat(clazz, is(equalTo(ReflectConstructorExample.class)));
        }

        @Test
        public void getName() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getConstructor();
            String name = cons.getName();
            assertThat(name, is("reflect.ReflectConstructorExample"));
        }
    }

    public static class getConstructorsの確認 {
        @Test
        public void getConstructorsでコンストラクタオブジェクトが取得できること() throws Exception {
            Class clazz = ReflectConstructorExample.class;
            Constructor[] cons = clazz.getConstructors();
            Constructor[] expected = {
                    clazz.getConstructor()
            };
            assertThat(cons, is(arrayContainingInAnyOrder(expected)));
        }
    }

    public static class getDeclaredConstructorの確認 {
        @Test
        public void getDeclaredConstructorでpublicコンストラクタが取得できること() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getDeclaredConstructor();
            int mod = cons.getModifiers();
            assertThat(mod, is(Modifier.PUBLIC));
        }

        @Test
        public void getDeclaredConstructorでprotectedコンストラクタが取得できること() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getDeclaredConstructor(String.class);
            int mod = cons.getModifiers();
            assertThat(mod, is(Modifier.PROTECTED));
        }

        @Test
        public void getDeclaredConstructorでdefaultコンストラクタが取得できること() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getDeclaredConstructor(String.class, int.class);
            int mod = cons.getModifiers();
            assertThat(mod, is(0));
        }

        @Test
        public void getDeclaredConstructorでprivateコンストラクタが取得できること() throws Exception {
            Constructor cons = ReflectConstructorExample.class.getDeclaredConstructor(int.class);
            int mod = cons.getModifiers();
            assertThat(mod, is(Modifier.PRIVATE));
        }
    }

    public static class getDeclaredConstructorsの確認 {
        @Test
        public void getDeclaredConstructorsでコンストラクタオブジェクトが取得できること() throws Exception {
            Class clazz = ReflectConstructorExample.class;
            Constructor[] cons = clazz.getDeclaredConstructors();
            Constructor[] expected = {
                    clazz.getDeclaredConstructor(),
                    clazz.getDeclaredConstructor(String.class),
                    clazz.getDeclaredConstructor(String.class, int.class),
                    clazz.getDeclaredConstructor(int.class)
            };
            assertThat(cons, is(arrayContainingInAnyOrder(expected)));
        }
    }

    public static class コンストラクタを利用したインスタンス生成確認 {
        @Test
        public void publicコンストラクタでのインスタンス生成() throws Exception {
            Class<ReflectConstructorExample> clazz = ReflectConstructorExample.class;
            ReflectConstructorExample ref = clazz.getConstructor().newInstance();
            assertThat(ref, is(instanceOf(ReflectConstructorExample.class)));
        }

        @Test
        public void protectedコンストラクタでのインスタンス生成() throws Exception {
            Class<ReflectConstructorExample> clazz = ReflectConstructorExample.class;
            ReflectConstructorExample ref = clazz.getDeclaredConstructor(String.class).newInstance("pro");
            assertThat(ref, is(instanceOf(ReflectConstructorExample.class)));
        }

        @Test
        public void defaultコンストラクタでのインスタンス生成() throws Exception {
            Class<ReflectConstructorExample> clazz = ReflectConstructorExample.class;
            ReflectConstructorExample ref = clazz.getDeclaredConstructor(String.class, int.class).newInstance("def", 19);
            assertThat(ref, is(instanceOf(ReflectConstructorExample.class)));
        }

        @Test(expected = IllegalAccessException.class)
        public void privateコンストラクタでのインスタンス生成ができないこと() throws Exception {
            Class<ReflectConstructorExample> clazz = ReflectConstructorExample.class;
            ReflectConstructorExample ref = clazz.getDeclaredConstructor(int.class).newInstance(20);
            assertThat(ref, is(instanceOf(ReflectConstructorExample.class)));
        }
    }
}