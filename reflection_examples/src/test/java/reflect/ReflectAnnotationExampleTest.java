package reflect;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static reflect.ReflectAnnotationExample.*;

/**
 * Created by pppurple on 2016/07/26.
 */
@RunWith(Enclosed.class)
public class ReflectAnnotationExampleTest {
    public static class Annotationクラス取得の確認 {
        @Test
        public void Classクラスからアノテーションが取得できること() throws Exception {
            Class clazz = ReflectAnnotationExample.DeprecatedClass.class;
            Annotation anno = clazz.getAnnotation(ClassAnnotation.class);
            assertThat(anno, is(ClassAnnotation.class));
        }

        @Test
        public void Methodクラスからアノテーションが取得できること() throws Exception {
            Method method = ReflectAnnotationExample.class.getMethod("getName");
            Annotation anno = method.getAnnotation(MethodAnnotation.class);
            assertThat(anno, is(MethodAnnotation.class));
        }

        @Test
        public void Fieldクラスからアノテーションが取得できること() throws Exception {
            Field field = ReflectAnnotationExample.class.getField("name");
            Annotation anno = field.getAnnotation(FieldAnnotation.class);
            assertThat(anno, is(FieldAnnotation.class));
        }

        @Test
        public void Constructorクラスからアノテーションが取得できること() throws Exception {
            Constructor cons = ReflectAnnotationExample.class.getConstructor(String.class);
            Annotation anno = cons.getAnnotation(ConstructorAnnotaion.class);
            assertThat(anno, is(ConstructorAnnotaion.class));
        }
    }
}