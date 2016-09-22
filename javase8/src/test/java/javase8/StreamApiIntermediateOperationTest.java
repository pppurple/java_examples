package javase8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pppurple on 2016/08/18.
 */
public class StreamApiIntermediateOperationTest {
    @Test
    public void filter() {
        // int
        IntStream.rangeClosed(0, 10)
                .filter(i -> i > 5)
                .forEach(System.out::println);

        List<Integer> intList = IntStream.rangeClosed(0, 10)
                .filter(i -> i > 5)
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(6, 7, 8, 9, 10);

        // String
        String[] texts = {"aaa", "bbb", "ccc", "abc"};
        Arrays.stream(texts)
                .filter(text -> text.endsWith("c"))
                .forEach(System.out::println);

        List<String> strList = Arrays.stream(texts)
                .filter(text -> text.endsWith("c"))
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("ccc", "abc");
    }

    @Test
    public void map() {
        // int -> int
        IntStream.rangeClosed(1, 10)
                .map(i -> i + 10)
                .forEach(System.out::println);

        List<Integer> intList = IntStream.rangeClosed(1, 10)
                .map(i -> i + 10)
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        // String -> String
        String[] texts = {"aaa", "bbb", "ccc"};
        Arrays.stream(texts)
                .map(text -> "|" + text + "|")
                .forEach(System.out::println);

        List<String> strList = Arrays.stream(texts)
                .map(text -> "|" + text + "|")
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("|aaa|", "|bbb|", "|ccc|");
    }

    @Test
    public void mapToInt() {
        // Stringからintへ変換
        String[] texts2 = {"aaa", "bbbb", "ccccc"};
        Arrays.stream(texts2)
                .mapToInt(String::length)
                .forEach(System.out::println);

        List<Integer> stringToInt = Arrays.stream(texts2)
                .mapToInt(String::length)
                .boxed()
                .collect(Collectors.toList());
        assertThat(stringToInt).containsOnly(3, 4, 5);
    }

    @Test
    public void mapToObj() {
        // intからStringへ変換
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> i + "yen")
                .forEach(System.out::println);

        List<String> intToString = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> i + "yen")
                .collect(Collectors.toList());
        assertThat(intToString).containsOnly("1yen", "2yen", "3yen", "4yen", "5yen");
    }

    @Test
    public void flatMap() {
        List<String> texts = Arrays.asList("aaa bbb ccc", "ddd eee fff");
        texts.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(String::toUpperCase)
                .forEach(System.out::println);

        List<String> list = texts.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertThat(list).containsOnly("AAA", "BBB", "CCC", "DDD", "EEE", "FFF");
    }

    @Test
    public void flatMapToInt() {
        List<String> texts = Arrays.asList("a bb ccc", "dddd eeeee ffffff");
        texts.stream()
                .flatMapToInt(s -> {
                    String[] str = s.split(" ");
                    return Arrays.stream(str).mapToInt(String::length);
                })
                .boxed()
                .forEach(System.out::println);

        List<Integer> list = texts.stream()
                .flatMapToInt(s -> {
                    String[] str = s.split(" ");
                    return Arrays.stream(str).mapToInt(String::length);
                })
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void distinct() {
        // int
        IntStream.of(0, 1, 1, 2, 2, 3, 2, 1)
                .distinct()
                .forEach(System.out::println);

        List<Integer> intList = IntStream.of(0, 1, 1, 2, 2, 3, 2, 1)
                .distinct()
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(0, 1, 2, 3);

        // String
        Stream.of("a", "b", "c", "b", "c")
                .distinct()
                .forEach(System.out::println);

        List<String> strList = Stream.of("a", "b", "c", "b", "c")
                .distinct()
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("a", "b", "c");
    }

    @Test
    public void sorted() {
        // string sort asc
        Stream.of("abc", "abb", "aab", "adb")
                .sorted()
                .forEach(System.out::println);
        Stream.of("abc", "abb", "aab", "adb")
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);

        List<String> strList = Stream.of("abc", "abb", "aab", "adb")
                .sorted()
                .collect(Collectors.toList());
        assertThat(strList).containsSequence("aab", "abb", "abc", "adb");

        // string sort desc
        Stream.of("abc", "abb", "aab", "adb")
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        List<String> reverseStrList = Stream.of("abc", "abb", "aab", "adb")
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertThat(reverseStrList).containsSequence("adb", "abc", "abb", "aab");

        // int sort asc
        IntStream.of(2, 3, 1, 4, 6)
                .sorted()
                .forEach(System.out::println);

        List<Integer> intList = IntStream.of(2, 3, 1, 4, 6)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
        assertThat(intList).containsSequence(1, 2, 3, 4, 6);

        // int sort desc
        IntStream.of(2, 3, 1, 4, 6)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        List<Integer> reverseIntList = IntStream.of(2, 3, 1, 4, 6)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertThat(reverseIntList).containsSequence(6, 4, 3, 2, 1);
    }

    @Test
    public void unsorted() {
        // int sort asc
        IntStream.rangeClosed(1, 10)
                .map(i -> i * 2)
                .unordered()
                .forEach(System.out::println);

        List<Integer> intList = IntStream.of(2, 3, 1, 4, 6)
                .boxed()
                .sorted()
                .collect(Collectors.toList());
        assertThat(intList).containsSequence(1, 2, 3, 4, 6);

    }

    @Test
    public void limit() {
        // iterate
        IntStream.iterate(0, i -> i + 100)
                .limit(5)
                .forEach(System.out::println);

        List<Integer> intList = IntStream.iterate(0, i -> i + 100)
                .boxed()
                .limit(5)
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(0, 100, 200, 300, 400);

        // generate
        Stream.generate(() -> "abc")
                .limit(5)
                .forEach(System.out::print);

        List<String> strList = Stream.generate(() -> "abc")
                .limit(5)
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("abc", "abc", "abc", "abc", "abc");
    }

    @Test
    public void skip() {
        // int
        IntStream.rangeClosed(0, 100)
                .skip(92)
                .forEach(System.out::println);

        List<Integer> intList = IntStream.rangeClosed(0, 100)
                .skip(92)
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsSequence(92, 93, 94, 95, 96, 97, 98, 99, 100);

        // String
        Stream.of("a", "b", "c", "d", "e", "f", "g", "h")
                .skip(4)
                .forEach(System.out::println);

        List<String> strList = Stream.of("a", "b", "c", "d", "e", "f", "g", "h")
                .skip(4)
                .collect(Collectors.toList());
        assertThat(strList).containsSequence("e", "f", "g", "h");
    }

    @Test
    public void peek() {
        Stream.of("a", "b", "c", "b", "c")
                .map(String::toUpperCase)
                .peek(s -> System.out.println("upper : " + s))
                .map(s -> s + s)
                .peek(System.out::println)
                .map(String::length)
                .forEach(System.out::println);

        List<Integer> list = Stream.of("a", "b", "c", "b", "c")
                .map(String::toUpperCase)
                .peek(s -> System.out.println("upper : " + s))
                .map(s -> s + s)
                .peek(System.out::println)
                .map(String::length)
                .collect(Collectors.toList());
        assertThat(list).containsOnly(2, 2, 2, 2, 2);
    }

    @Test
    public void boxed() {
        Stream<Integer> stream = IntStream.rangeClosed(0, 10)
                .boxed();
        stream.forEach(System.out::println);

        List<Integer> list = IntStream.rangeClosed(0, 10)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    public void asLongStream() {
        IntStream.rangeClosed(1, 10)
                .asLongStream()
                .forEach(System.out::println);

        List<Long> list = IntStream.rangeClosed(1, 10)
                .asLongStream()
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
    }
}
