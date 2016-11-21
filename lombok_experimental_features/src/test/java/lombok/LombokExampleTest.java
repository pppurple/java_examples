package lombok;

import lombok.experimental.Accessors;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class LombokExampleTest {

    public static class AccessorsExample {

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

//                @FieldDefaults(level = AccessLevel.PRIVATE)
    public class FieldLevelPrivate {
        private String foo = "ABC";
    }

    @Test
    public void FieldLevelPrivateTest() {
        FieldLevelPrivate fp = new FieldLevelPrivate();
        fp.foo = "Ddd";
    }

    public static class FieldDefaultExample {

        @FieldDefaults(makeFinal = true)
        public class FieldFinal {
            int num = 100;
        }

        @Test
        public void FieldFinalTest() {
            FieldFinal ff = new FieldFinal();

            // error
            // ff.num = 200;

            assertThat(ff.num).isEqualTo(100);
        }

        @Test
        public void FieldLevelPrivateTest() {
            FieldLevelPrivate fp = new FieldLevelPrivate();
            fp.foo = "Ddd";
        }


    }
}


