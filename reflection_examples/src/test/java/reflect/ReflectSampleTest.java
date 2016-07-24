package reflect;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
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


}