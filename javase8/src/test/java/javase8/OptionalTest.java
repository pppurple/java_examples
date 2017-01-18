package javase8;

import lombok.Value;
import org.junit.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalTest {
    @Test
    public void of() {
        Zoo zoo = new Zoo("zoo");
        Optional<Zoo> ofZoo = Optional.of(zoo);

        assertThat(ofZoo.get().getClass()).isEqualTo(Zoo.class);
    }

    @Test(expected = NullPointerException.class)
    public void ofButNull() {
        Zoo zoo = null;
        Optional<Zoo> ofZoo = Optional.of(zoo);

        assertThat(ofZoo.get().getClass()).isEqualTo(Zoo.class);
    }

    @Test
    public void ofNullable() {
        Zoo zoo = new Zoo("zoo");
        Optional<Zoo> ofNullableZoo = Optional.ofNullable(zoo);

        assertThat(ofNullableZoo.get().getClass()).isEqualTo(Zoo.class);

        Zoo zooNull = null;
        Optional<Zoo> ofNullableZooNull = Optional.ofNullable(zooNull);

        assertThat(ofNullableZooNull.isPresent()).isFalse();
    }

    @Test
    public void get() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("bbb"))
                .findAny();
        String get = any.get();
        assertThat(get).isEqualTo("bbb");
    }

    @Test
    public void getAsInt() {
        OptionalInt intAny = IntStream.rangeClosed(1, 10)
                .filter(i -> i == 3)
                .findAny();
        int getInt = intAny.getAsInt();
        assertThat(getInt).isEqualTo(3);
    }

    @Test(expected = NoSuchElementException.class)
    public void getWithException() throws Exception {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("ddd"))
                .findAny();
        String get = any.get();
    }

    @Test
    public void ifPresent() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("bbb"))
                .findAny();
        any.ifPresent(System.out::println);
    }

    @Test
    public void isPresent() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("bbb"))
                .findAny();
        assertThat(any.isPresent()).isTrue();
    }

    @Test
    public void orElse() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("ddd"))
                .findAny();
        String get = any.orElse("fallback");
        assertThat(get).isEqualTo("fallback");

        OptionalInt intAny = IntStream.rangeClosed(1, 10)
                .filter(i -> i == 12)
                .findAny();
        int getInt = intAny.orElse(-1);
        assertThat(getInt).isEqualTo(-1);
    }

    @Test
    public void orElseGet() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("ddd"))
                .findAny();
        String get = any.orElseGet(() -> "eee");
        assertThat(get).isEqualTo("eee");

        OptionalInt intAny = IntStream.rangeClosed(1, 10)
                .filter(i -> i == 12)
                .findAny();
        // IntSupplier
        int getInt = intAny.orElseGet(() -> -1);
        assertThat(getInt).isEqualTo(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orElseThrow() throws Exception {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("ddd"))
                .findAny();
        String get = any.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void filter() {
        String text = Stream.of("aa", "ab", "ba", "bb")
                .filter(s -> s.endsWith("b"))
                .findAny()
                .filter(s -> s.startsWith("a"))
                .get();
        assertThat(text).isEqualTo("ab");
    }

    @Test
    public void map() {
        String text = Stream.of("aa", "ab", "ba", "bb")
                .filter(s -> s.equals("ab"))
                .findAny()
                .map(String::toUpperCase)
                .get();
        assertThat(text).isEqualTo("AB");
    }

    @Test
    public void flatMap() {
        Optional<String> text = Stream.of("aaa", "bbb", "ccc")
                .max(Comparator.naturalOrder());
        Optional<Integer> num = Stream.of(3, 5, 2, 8)
                .max(Comparator.naturalOrder());

        Optional<String> max = text.flatMap(s ->
                num.flatMap(i -> Optional.of(s + i))
        );

        String result = max.get();
        assertThat(result).isEqualTo("ccc8");
    }

    @Value
    private static class Zoo {
        String name;
    }
}
