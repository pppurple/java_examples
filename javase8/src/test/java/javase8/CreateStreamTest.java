package javase8;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

/**
 * Created by pppurple on 2016/08/14.
 */
public class CreateStreamTest {
    @Test
    public void リストからのストリーム処理() {
        // Stringのリスト
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
        Stream<String> StrStream = list.stream();
        StrStream.forEach(System.out::println);

        Stream<String> strStream2 = list.stream();
        List<String> listFromStream = strStream2.collect(Collectors.toList());
        assertThat(listFromStream).containsOnly("aaa", "bbb", "ccc");

        // Integerのリスト
        List<Integer> intList = Arrays.asList(1, 2, 3);
        Stream<Integer> intStream = intList.stream();
        intStream.forEach(System.out::println);

        Stream<Integer> intStream2 = intList.stream();
        List<Integer> intListFromStream = intStream2.collect(Collectors.toList());
        assertThat(intListFromStream).containsOnly(1, 2, 3);
    }

    @Test
    public void 配列からのストリーム処理() {
        // Stringの配列
        String[] array = {"aaa", "bbb", "ccc", "eee", "fff"};
        Stream<String> stream = Arrays.stream(array);
        stream.forEach(System.out::println);

        Stream<String> stream2 = Arrays.stream(array);
        String[] arrayFromStream = stream2.toArray(String[]::new);
        assertThat(arrayFromStream).containsOnly("aaa", "bbb", "ccc", "eee", "fff");

        // Stringの配列(index指定)
        Stream<String> streamWithIndex = Arrays.stream(array, 2, 4);
        streamWithIndex.forEach(System.out::println);

        Stream<String> streamWithIndex2 = Arrays.stream(array, 2, 4);
        String[] arrayWithIndex = streamWithIndex2.toArray(String[]::new);
        assertThat(arrayWithIndex).containsOnly("ccc", "eee");

        // intの配列
        int[] intArray = {100, 200, 300, 400, 500};
        IntStream intStream = Arrays.stream(intArray);
        intStream.forEach(System.out::println);

        IntStream intStream2 = Arrays.stream(intArray);
        int[] intArrayFromStream = intStream2.toArray();
        assertThat(intArrayFromStream).containsOnly(100, 200, 300, 400, 500);
    }

    @Test
    public void ファイル読み込みのストリーム処理() throws Exception {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/javase8/memo.txt"))) {
            Stream<String> stream = reader.lines();
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw e;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/javase8/memo.txt"))) {
            Stream<String> stream2 = reader.lines();
            List<String> list = stream2.collect(Collectors.toList());
            assertThat(list).containsOnly("aiueo", "かきくけこ");
        } catch (IOException e) {
            throw e;
        }

        Path path = Paths.get("src/main/java/javase8/memo.txt");
        try(Stream<String> stream = Files.lines(path)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw e;
        }
    }

    @Test
    public void Filesからのストリーム生成() {
        Path path = Paths.get("src/main/java/javase8");
        try {
            Files.list(path).forEach(System.out::println);
            Files.walk(path).forEach(System.out::println);
            Files.find(path, 2,
                    (p, attr) -> p.toString().endsWith("java"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ofによるストリーム生成() {
        // StringのStream
        Stream<String> stream = Stream.of("aaa", "bbb", "ccc");
        List<String> list = stream.collect(Collectors.toList());
        assertThat(list).containsOnly("aaa", "bbb", "ccc");

        // IntStream
        IntStream intStream = IntStream.of(100, 200, 300);
        List<Integer> intList = intStream.boxed().collect(Collectors.toList());
        assertThat(intList).containsOnly(100, 200, 300);

        // 配列からのStream
        String[] array = {"AAA", "BBB", "CCC"};
        Stream<String> streamArray = Stream.of(array);
        List<String> listFromArray = streamArray.collect(Collectors.toList());
        assertThat(listFromArray).containsOnly("AAA", "BBB", "CCC");
    }

    @Test
    public void rangeによるストリーム生成() {
        // 0から9まで出力
        IntStream.range(0, 10).forEach(System.out::println);

        IntStream streamRange = IntStream.range(0, 10);
        List<Integer> listRange = streamRange.boxed().collect(Collectors.toList());
        assertThat(listRange).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 0から10まで出力
        IntStream.rangeClosed(0, 10).forEach(System.out::println);

        IntStream streamRangeClosed = IntStream.rangeClosed(0, 10);
        List<Integer> listRangeClosed = streamRangeClosed.boxed().collect(Collectors.toList());
        assertThat(listRangeClosed).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    public void iterateによるストリーム生成() {
        IntStream.iterate(0, i -> i + 100)
                .limit(10)
                .forEach(System.out::println);

        List<Integer> list = IntStream.iterate(0, i -> i + 100)
                .limit(10)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(0, 100, 200, 300, 400, 500, 600, 700, 800, 900);
    }

    @Test
    public void generateによるストリーム生成() {
        Stream.generate(() -> "abc")
                .limit(5)
                .forEach(System.out::println);

        List<String> list = Stream.generate(() -> "abc")
                .limit(5)
                .collect(Collectors.toList());
        assertThat(list).containsOnly("abc", "abc", "abc", "abc", "abc");
    }

    @Test
    public void emptyによるストリーム生成() {
        IntStream.empty().forEach(System.out::println);

        IntStream stream = IntStream.empty();
        List<Integer> list = stream.boxed().map(i -> i + 1).collect(Collectors.toList());
        assertThat(list).isEmpty();
    }

    @Test
    public void concatによるストリーム生成() {
        IntStream stream1 = IntStream.range(0, 5);
        IntStream stream2 = IntStream.range(5, 10);
        IntStream.concat(stream1, stream2).forEach(System.out::println);

        List<Integer> list = IntStream.concat(IntStream.range(0,5), IntStream.range(5, 10))
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void Randomからのストリーム生成() {
        Random random = new Random(100);
        random.ints(5, 1, 1000).forEach(System.out::println);

        Random random2 = new Random(100);
        List<Integer> list = random2.ints(5, 1, 1000)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(971, 506, 2, 703, 440);
    }

    @Test
    public void charsからのストリーム生成() {
        String str = "abcde1234あいう";
        String emoji = "\uD83D\uDE04";
        str.chars()
                .forEach(System.out::println);
        str.chars()
                .forEach(c -> System.out.println((char)c));
        emoji.codePoints()
                .forEach(c -> System.out.printf("%X", c));
    }
}
