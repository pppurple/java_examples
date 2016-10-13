package javase8;

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
    public void get() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("bbb"))
                .findAny();
        String get = any.get();
        assertThat(get).isEqualTo("bbb");

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
    public void orElse() {
        Optional<String> any = Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("ddd"))
                .findAny();
        String get = any.orElse("defo");
        assertThat(get).isEqualTo("defo");

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
        String get = any.orElseThrow(() -> new IllegalArgumentException());
    }

    @Test
    public void ifPresent() {
        Stream.of("aaa", "bbb", "ccc")
                .filter(s -> s.endsWith("bbb"))
                .findAny()
                .ifPresent(System.out::println);
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
}
