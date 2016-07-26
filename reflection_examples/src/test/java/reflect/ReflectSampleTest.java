package reflect;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.*;

/**
 * Created by pppurple on 2016/07/23.
 */
@RunWith(Enclosed.class)
public class ReflectSampleTest {
    public static class getFieldの確認 {
        @Test
        public void getTypeでpublicフィールドの型が取得できること() throws Exception {
            Field field = ReflectSample.class.getField("pub");
            Class type = field.getType();
            assertThat(type, is(equalTo(String.class)));
        }

        @Test(expected = java.lang.NoSuchFieldException.class)
        public void getTypeでprotectedフィールドの型が取得できないこと() throws Exception {
            Field field = ReflectSample.class.getField("pro");
        }

        @Test(expected = java.lang.NoSuchFieldException.class)
        public void getTypeでdefaultフィールドの型が取得できないこと() throws Exception {
            Field field = ReflectSample.class.getField("def");
        }

        @Test(expected = java.lang.NoSuchFieldException.class)
        public void getTypeでprivateフィールドの型が取得できないこと() throws Exception {
            Field field = ReflectSample.class.getField("pri");
        }

        @Test
        public void getNameでフィールドの名前が取得できること() throws Exception {
            Field field = ReflectSample.class.getField("pub");
            String name = field.getName();
            assertThat(name, is("pub"));
        }

        @Test
        public void getModifiersでフィールドのアクセス修飾子が取得できること() throws Exception {
            Field field = ReflectSample.class.getField("pub");
            int mod = field.getModifiers();
            assertThat(mod, is(Modifier.PUBLIC));
        }

        @Test
        public void toGenericStringでフィールドを表す文字列が取得できること() throws Exception {
            Field field = ReflectSample.class.getField("pub");
            String generic = field.toGenericString();
            assertThat(generic, is("public java.lang.String reflect.ReflectSample.pub"));
        }
    }

    public static class getFieldsの確認 {
        @Test
        public void getFieldsでフィールドオブジェクトが取得できること() throws Exception {
            Class clazz = ReflectSample.class;
            Field[] fields = clazz.getFields();
            Field[] expected = {
                    clazz.getField("pub")
            };
            assertThat(fields, is(arrayContainingInAnyOrder(expected)));
        }
    }

    public static class getDeclaredFieldの確認 {
        @Test
        public void getModifiersでpublicフィールドのアクセス修飾子が取得できること() throws Exception {
            Field field = ReflectSample.class.getDeclaredField("pub");
            int mod = field.getModifiers();
            assertThat(mod, is(Modifier.PUBLIC));
        }

        @Test
        public void getModifiersでprotectedフィールドのアクセス修飾子が取得できること() throws Exception {
            Field field = ReflectSample.class.getDeclaredField("pro");
            int mod = field.getModifiers();
            assertThat(mod, is(Modifier.PROTECTED));
        }

        @Test
        public void getModifiersでdefaultフィールドのアクセス修飾子が取得できること() throws Exception {
            Field field = ReflectSample.class.getDeclaredField("def");
            int mod = field.getModifiers();
            assertThat(mod, is(0));
        }

        @Test
        public void getModifiersでprivateフィールドのアクセス修飾子が取得できること() throws Exception {
            Field field = ReflectSample.class.getDeclaredField("pri");
            int mod = field.getModifiers();
            assertThat(mod, is(Modifier.PRIVATE));
        }
    }

    public static class getDeclaredFieldsの確認 {
        @Test
        public void getDeclaredFieldsでフィールドオブジェクトが取得できること() throws Exception {
            Class clazz = ReflectSample.class;
            Field[] fields = clazz.getDeclaredFields();
            Field[] expected = {
                    clazz.getDeclaredField("pub"),
                    clazz.getDeclaredField("pro"),
                    clazz.getDeclaredField("def"),
                    clazz.getDeclaredField("pri"),
                    clazz.getDeclaredField("name")
            };
            assertThat(fields, is(arrayContainingInAnyOrder(expected)));
        }
    }
}