package lombok;

import lombok.experimental.Accessors;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;

import static lombok.FieldDefaultsExample.FieldLevelPrivate;
import static lombok.FieldDefaultsExample.FieldLevelPublic;
import static lombok.FieldDefaultsExample.FieldFinal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class LombokExampleTest {

    public static class AccessorsExampleTest {

        @Accessors(chain = true)
        public class AccessorsChain {
            @Setter
            String bar;

           void printBar() {
                System.out.println("|" + this.bar + "|");
           }
        }

        @Test
        public void AccessorsChainTest() {
            AccessorsChain chain = new AccessorsChain();
            chain.setBar("AAA").printBar();

            assertThat(chain.setBar("AAA").getClass()).isEqualTo(AccessorsChain.class);
        }

        @Accessors(fluent = true)
        public class AccessorsFluent {
            @Getter
            @Setter
            private String foo = "abc";
        }

        @Test
        public void accessorsFluentTest() {
            AccessorsFluent acc = new AccessorsFluent();

            // getter
            acc.foo();
            assertThat(acc.foo()).isEqualTo("abc");

            // setter
            acc.foo("ccc");
            assertThat(acc.foo()).isEqualTo("ccc");
        }

        public class AccessorsPrefix {
            @Getter
            @Setter
            @Accessors(prefix = "pre")
            String preZoo;
        }

        @Test
        public void AccessorsPrefixTest() {
            AccessorsPrefix p = new AccessorsPrefix();

            // getter
            p.setZoo("AAA");

            // setter
            p.getZoo();

            assertThat(p.getZoo()).isEqualTo("AAA");
        }
    }

    public static class FieldDefaultsExampleTest {

        @Test
        public void fieldPrivateTest() {
            FieldLevelPrivate pri = new FieldLevelPrivate();

            // error
            // pri.text;

            assertThat(pri.getText()).isEqualTo("ABC");
        }

        @Test
        public void fieldPublicTest() {
            FieldLevelPublic pub = new FieldLevelPublic();

            assertThat(pub.num).isEqualTo(100);

            pub.num = 200;
            assertThat(pub.num).isEqualTo(200);
        }

        @Test
        public void FieldFinalTest() {
            FieldFinal ff = new FieldFinal();

            // error
            // ff.num = 200;

            assertThat(ff.num).isEqualTo(200);
        }
    }

    public static class WitherExampleTest {

        @Test
        public void WitherTest() {
            WitherExample origin = new WitherExample("abc", 123);

            WitherExample generatedWith = origin.withName("BBB");

            assertThat(origin).isNotEqualTo(generatedWith);
            assertThat(generatedWith.getAge()).isEqualTo(123);
            assertThat(generatedWith.getName()).isEqualTo("BBB");
        }
    }

    public static class onXExampleTest {

        @Test
        public void OnXTest() throws NoSuchMethodException {

            // constructor
            Constructor con = OnXExample.class.getConstructor(String.class, int.class);
            Annotation anoCons = con.getAnnotation(OnXExample.ConstructorAnnotation.class);
            assertThat(anoCons).isInstanceOf(OnXExample.ConstructorAnnotation.class);

            // setter
            Method name = OnXExample.class.getMethod("setName", String.class);
            Parameter[] paraSetter = name.getParameters();
            Annotation anoSetter = paraSetter[0].getAnnotation(Min.class);
            assertThat(anoSetter).isInstanceOf(Min.class);

            // getter
            Method num = OnXExample.class.getMethod("getNum");
            Annotation anoGetter = num.getAnnotation(OnXExample.MethodAnnotation.class);
            assertThat(anoGetter).isInstanceOf(OnXExample.MethodAnnotation.class);
        }
    }

    public static class UtilityExampleTest {

        @Test
        public void UtilityTest() {
            // error
            // UtilityExample util = new UtilityExample();

            int magicNum = UtilityExample.MAGIC_NUMBER;
            assertThat(magicNum).isEqualTo(10);

            int doubleNum = UtilityExample.doubleNum(200);
            assertThat(doubleNum).isEqualTo(400);
        }
    }
}


