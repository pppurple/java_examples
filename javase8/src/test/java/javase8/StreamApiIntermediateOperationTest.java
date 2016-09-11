package javase8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pppurple on 2016/08/18.
 */
public class StreamApiIntermediateOperationTest {
    @Test
    public void filter() {
        IntStream.rangeClosed(0, 10)
                .filter(i -> i > 5)
                .forEach(System.out::println);

        String[] texts = {"aaa", "bbb", "ccc", "abc"};
        Arrays.stream(texts)
                .filter(text -> text.endsWith("c"))
                .forEach(System.out::println);
    }

    @Test
    public void map() {
        IntStream.rangeClosed(1, 10)
                .map(i -> i + 10)
                .forEach(System.out::println);

        String[] texts = {"aaa", "bbb", "ccc"};
        Arrays.stream(texts)
                .map(text -> "|" + text + "|")
                .forEach(System.out::println);

        // intからStringへ変換
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> i + "yen")
                .forEach(System.out::println);

        // Stringからintへ変換
        String[] texts2 = {"aaa", "bbbb", "ccccc"};
        Arrays.stream(texts2)
                .mapToInt(text -> text.length())
                .forEach(System.out::println);
    }

    @Test
    public void flatMap() {
        List<String> csvs = Arrays.asList("aaa bbb ccc",
                "ddd eee fff");
        csvs.stream()
                .flatMap(csv -> Arrays.stream(csv.split(" ")))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    public void distinct() {
        IntStream.of(0, 1, 1, 2, 2, 3, 2)
                .distinct()
                .forEach(System.out::println);

        Stream.of("a", "b", "c", "b", "c")
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void sorted() {
        Stream.of("abc", "abb", "aab", "adb")
                .sorted()
                .forEach(System.out::println);

        Stream.of("abc", "abb", "aab", "adb")
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        IntStream.of(2, 3, 1, 4, 6)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void limit() {
        IntStream.iterate(0, i -> i + 100)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void skip() {
        IntStream.rangeClosed(0, 100)
                .skip(80)
                .forEach(System.out::println);
    }

    @Test
    public void peek() {
        Stream.of("a", "b", "c", "b", "c")
                .map(String::toUpperCase)
                .peek(s -> System.out.println("upper : " + s))
                .map(s -> s + s)
                .peek(System.out::println)
                .map(s -> s.length())
                .forEach(System.out::println);
    }

    @Test
    public void boxed() {
        Stream<Integer> stream = IntStream.rangeClosed(0, 10)
                .boxed();
    }
}
