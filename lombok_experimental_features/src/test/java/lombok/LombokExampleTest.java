package lombok;

import lombok.experimental.Accessors;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LombokExampleTest {

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
