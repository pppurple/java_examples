package lombok;

import lombok.experimental.Accessors;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class LombokExampleTest {

    public class AccessorsTest {

        @Accessors(fluent = true)
        public class AccessorsExample {
            @Getter
            @Setter
            private String foo = "abc";
        }

        @Test
        public void accessorsTest() {
            AccessorsExample acc = new AccessorsExample();

            // getter
            acc.foo();
            assertThat(acc.foo()).isEqualTo("abc");

            // setter
            acc.foo("ccc");
            assertThat(acc.foo()).isEqualTo("ccc");
        }
    }
}
