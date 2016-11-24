package lombok;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import lombok.FieldDefaultsExample.FieldLevelPrivate;
import lombok.FieldDefaultsExample.FieldLevelPublic;
import lombok.FieldDefaultsExample.FieldFinal;
import lombok.AccessorsExample.AccessorsChain;
import lombok.AccessorsExample.AccessorsFluent;
import lombok.AccessorsExample.AccessorsPrefix;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class LombokExampleTest {

    public static class AccessorsExampleTest {

        @Test
        public void AccessorsChainTest() {
            AccessorsChain chain = new AccessorsChain();
            chain.setBar("AAA").printBar();

            assertThat(chain.setBar("AAA").getClass()).isEqualTo(AccessorsChain.class);
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

            // error!!
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

            // final
            // ff.num = 200; // error!!
            assertThat(ff.count).isEqualTo(200);

            // non final
            ff.memo = "new memo";
            assertThat(ff.memo).isEqualTo("new memo");
        }
    }

    public static class WitherExampleTest {

        @Test
        public void WitherTest() {
            WitherExample origin = new WitherExample("abc", 123);

            WitherExample newNameByWith = origin.withName("BBB");

            assertThat(origin).isNotEqualTo(newNameByWith);
            assertThat(newNameByWith.getAge()).isEqualTo(123);
            assertThat(newNameByWith.getName()).isEqualTo("BBB");

            WitherExample newAgeByWith = origin.withAge(1_000);

            assertThat(origin).isNotEqualTo(newAgeByWith);
            assertThat(newAgeByWith.getAge()).isEqualTo(1_000);
            assertThat(newAgeByWith.getName()).isEqualTo("abc");
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


