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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pppurple on 2016/08/14.
 */
public class CreateStreamTest {
    @Test
    public void ofによるストリーム生成() {
        // StringのStream
        Stream<String> strStream = Stream.of("aaa", "bbb", "ccc");
        strStream.forEach(System.out::println);

        Stream<String> strStream2 = Stream.of("aaa", "bbb", "ccc");
        List<String> list = strStream2.collect(Collectors.toList());
        assertThat(list).containsOnly("aaa", "bbb", "ccc");

        // IntStream
        IntStream intStream = IntStream.of(100, 200, 300);
        intStream.forEach(System.out::println);

        IntStream intStream2 = IntStream.of(100, 200, 300);
        List<Integer> intList = intStream2.boxed().collect(Collectors.toList());
        assertThat(intList).containsOnly(100, 200, 300);

        // LongStream
        LongStream longStream = LongStream.of(10L, 20L, 30L);
        longStream.forEach(System.out::println);

        LongStream longStream2 = LongStream.of(10L, 20L, 30L);
        List<Long> longList = longStream2.boxed().collect(Collectors.toList());
        assertThat(longList).containsOnly(10L, 20L, 30L);

        // DoubleStream
        DoubleStream doubleStream = DoubleStream.of(1.0, 2.0, 3.0);
        doubleStream.forEach(System.out::println);

        DoubleStream doubleStream2 = DoubleStream.of(1.0, 2.0, 3.0);
        List<Double> doubleList = doubleStream2.boxed().collect(Collectors.toList());
        assertThat(doubleList).containsOnly(1.0, 2.0, 3.0);

        // 配列からのStream
        String[] array = {"AAA", "BBB", "CCC"};
        Stream<String> streamArray = Stream.of(array);
        streamArray.forEach(System.out::println);

        Stream<String> streamArray2 = Stream.of(array);
        List<String> listFromArray = streamArray2.collect(Collectors.toList());
        assertThat(listFromArray).containsOnly("AAA", "BBB", "CCC");
    }

    @Test
    public void リストからのストリーム生成() {
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
    public void 配列からのストリーム生成() {
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

        // longの配列
        long[] longArray = {10L, 20L, 30L};
        LongStream longStream = Arrays.stream(longArray);
        longStream.forEach(System.out::println);

        LongStream longStream2 = Arrays.stream(longArray);
        long[] longArrayFromStream = longStream2.toArray();
        assertThat(longArrayFromStream).containsOnly(10L, 20L, 30L);

        // doubleの配列
        double[] doubleArray = {1.0, 2.0, 3.0};
        DoubleStream doubleStream = Arrays.stream(doubleArray);
        doubleStream.forEach(System.out::println);

        DoubleStream doubleStream2 = Arrays.stream(doubleArray);
        double[] doubleArrayFromStream = doubleStream2.toArray();
        assertThat(doubleArrayFromStream).containsOnly(1.0, 2.0, 3.0);
    }

    @Test
    public void rangeによるストリーム生成() {
        // IntStream
        IntStream.range(0, 10).forEach(System.out::println);

        IntStream intStream = IntStream.range(0, 10);
        List<Integer> intList = intStream.boxed().collect(Collectors.toList());
        assertThat(intList).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        // LongStream
        LongStream.range(0L, 10L).forEach(System.out::println);

        LongStream longStream = LongStream.range(0L, 10L);
        List<Long> longList = longStream.boxed().collect(Collectors.toList());
        assertThat(longList).containsOnly(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);

        // error!!
        // DoubleStream.range(0.0, 10.0);
    }

    @Test
    public void rangeClosedによるストリーム生成() {
        // IntStream
        IntStream.rangeClosed(0, 10).forEach(System.out::println);

        IntStream intStream = IntStream.rangeClosed(0, 10);
        List<Integer> intList = intStream.boxed().collect(Collectors.toList());
        assertThat(intList).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // LongStream
        LongStream.rangeClosed(0L, 10L).forEach(System.out::println);

        LongStream longStream = LongStream.rangeClosed(0L, 10L);
        List<Long> longList = longStream.boxed().collect(Collectors.toList());
        assertThat(longList).containsOnly(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

        // error!!
        // DoubleStream.rangeClosed(0.0, 10.0);
    }

    @Test
    public void iterateによるストリーム生成() {
        // IntStream
        IntStream.iterate(0, i -> i + 100)
                .limit(10)
                .forEach(System.out::println);

        List<Integer> intList = IntStream.iterate(0, i -> i + 100)
                .limit(10)
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(0, 100, 200, 300, 400, 500, 600, 700, 800, 900);

        // LongStream
        LongStream.iterate(0L, i -> i + 10)
                .limit(5)
                .forEach(System.out::println);

        List<Long> longList = LongStream.iterate(0L, i -> i + 10)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        assertThat(longList).containsOnly(0L, 10L, 20L, 30L, 40L);

        // DoubleStream
        DoubleStream.iterate(0.0, i -> i + 1.0)
                .limit(5)
                .forEach(System.out::println);

        List<Double> doubleList = DoubleStream.iterate(0.0, i -> i + 1.0)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        assertThat(doubleList).containsOnly(0.0, 1.0, 2.0, 3.0, 4.0);

        // Stream
        Stream.iterate("a", s -> "[" + s + "]")
                .limit(5)
                .forEach(System.out::println);

        List<String> strList = Stream.iterate("a", s -> "[" + s + "]")
                .limit(5)
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("a", "[a]", "[[a]]", "[[[a]]]", "[[[[a]]]]");
    }

    @Test
    public void generateによるストリーム生成() {
        // Stream
        Stream.generate(() -> "abc")
                .limit(5)
                .forEach(System.out::println);

        List<String> strList = Stream.generate(() -> "abc")
                .limit(5)
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("abc", "abc", "abc", "abc", "abc");

        // IntStream
        IntStream.generate(() -> 123)
                .limit(5)
                .forEach(System.out::println);

        List<Integer> intList = IntStream.generate(() -> 123)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(123, 123, 123, 123, 123);

        // LongStream
        LongStream.generate(() -> 10L)
                .limit(5)
                .forEach(System.out::println);

        List<Long> longList = LongStream.generate(() -> 10L)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        assertThat(longList).containsOnly(10L, 10L, 10L, 10L, 10L);

        // DoubleStream
        DoubleStream.generate(() -> 10.0)
                .limit(5)
                .forEach(System.out::println);

        List<Double> doubleList = DoubleStream.generate(() -> 10.0)
                .limit(5)
                .boxed()
                .collect(Collectors.toList());
        assertThat(doubleList).containsOnly(10.0, 10.0, 10.0, 10.0, 10.0);
    }

    @Test
    public void concatによるストリーム生成() {
        // Stream
        Stream<String> stream1 = Stream.of("a", "b", "c", "d", "e");
        Stream<String> stream2 = Stream.of("A", "B", "C", "D", "E");
        Stream.concat(stream1, stream2).forEach(System.out::println);

        List<String> strList = Stream.concat(Stream.of("a", "b", "c", "d", "e"),
                Stream.of("A", "B", "C", "D", "E"))
                .collect(Collectors.toList());
        assertThat(strList).containsOnly("a", "b", "c", "d", "e", "A", "B", "C", "D", "E");

        // IntStream
        IntStream intStream1 = IntStream.range(0, 5);
        IntStream intStream2 = IntStream.range(5, 10);
        IntStream.concat(intStream1, intStream2).forEach(System.out::println);

        List<Integer> intList = IntStream.concat(IntStream.range(0, 5), IntStream.range(5, 10))
                .boxed()
                .collect(Collectors.toList());
        assertThat(intList).containsOnly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        // LongStream
        LongStream longStream1  = LongStream.range(0L, 5L);
        LongStream longStream2  = LongStream.range(5L, 10L);
        LongStream.concat(longStream1, longStream2).forEach(System.out::println);

        List<Long> longList = LongStream.concat(LongStream.range(0L, 5L), LongStream.range(5L, 10L))
                .boxed()
                .collect(Collectors.toList());
        assertThat(longList).containsOnly(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);

        // DoubleStream
        DoubleStream doubleStream1 = DoubleStream.of(0.0, 1.0, 2.0);
        DoubleStream doubleStream2 = DoubleStream.of(3.0, 4.0, 5.0);
        DoubleStream.concat(doubleStream1, doubleStream2).forEach(System.out::println);

        List<Double> doubleList = DoubleStream.concat(DoubleStream.of(0.0, 1.0, 2.0),
                DoubleStream.of(3.0, 4.0, 5.0))
                .boxed()
                .collect(Collectors.toList());
        assertThat(doubleList).containsOnly(0.0, 1.0, 2.0, 3.0, 4.0, 5.0);
    }

    @Test
    public void charsからのストリーム生成() {
        // chars
        String str = "abcde1234あいう";
        str.chars()
                .forEach(System.out::println);
        str.chars()
                .forEach(c -> System.out.println((char)c));

        int[] charArray = str.chars()
                .toArray();
        assertThat(charArray).containsOnly(97, 98, 99, 100, 101,
                49, 50, 51, 52,
                12354, 12356, 12358);
    }

    @Test
    public void codePointsからのストリーム生成() {
        // codePoints
        String emoji = "\uD83D\uDE04";
        emoji.codePoints()
                .forEach(System.out::println);
        emoji.codePoints()
                .mapToObj(Integer::toHexString)
                .forEach(System.out::print);

        int[] emojiInt = emoji.codePoints().toArray();
        assertThat(emojiInt).containsOnly(128516);
    }

    @Test
    public void Randomからのストリーム生成() {
        // ints
        Random intRandom = new Random(100);
        intRandom.ints(5, 1, 1000).forEach(System.out::println);

        List<Integer> list = intRandom.ints(5, 1, 1000)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsOnly(850, 399, 777, 503, 830);

        // longs
        Random longRandom = new Random(100);
        longRandom.longs(5, 1, 1000).forEach(System.out::println);

        List<Long> longList = longRandom.longs(5, 1, 1000)
                .boxed()
                .collect(Collectors.toList());
        assertThat(longList).containsOnly(36L, 55L, 415L, 942L, 722L);

        // doubles
        Random doubleRandom = new Random(100);
        doubleRandom.doubles(5, 1, 1000).forEach(System.out::println);

        List<Integer> doubleList = doubleRandom.doubles(5, 1, 1000)
                .boxed()
                .mapToInt(Double::intValue)
                .boxed()
                .collect(Collectors.toList());
        assertThat(doubleList).containsOnly(623, 237, 487, 680, 525);
    }

    @Test
    public void emptyによるストリーム生成() {
        Stream.empty().forEach(System.out::println);
        IntStream.empty().forEach(System.out::println);
        LongStream.empty().forEach(System.out::println);
        DoubleStream.empty().forEach(System.out::println);

        IntStream stream = IntStream.empty();
        List<Integer> list = stream.boxed().map(i -> i + 1).collect(Collectors.toList());
        assertThat(list).isEmpty();
    }

    @Test
    public void ファイル読み込みからのストリーム生成() throws IOException {
        // BufferReader
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/text/memo.txt"))) {
            Stream<String> stream = reader.lines();
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw e;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/text/memo.txt"))) {
            Stream<String> stream2 = reader.lines();
            List<String> list = stream2.collect(Collectors.toList());
            assertThat(list).containsOnly("aiueo", "かきくけこ");
        } catch (IOException e) {
            throw e;
        }

        // Files
        Path path = Paths.get("src/main/java/text/memo.txt");
        try(Stream<String> stream = Files.lines(path)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw e;
        }

        try(Stream<String> stream = Files.lines(path)) {
            List<String> list = stream.collect(Collectors.toList());
            assertThat(list).containsOnly("aiueo", "かきくけこ");
        } catch (IOException e) {
            throw e;
        }
    }

    @Test
    public void ディレクトリ読み込みからのストリーム生成() {
        Path path = Paths.get("src/main/java/text");

        // find
        try {
            Files.find(path, 2, (p, attr) -> p.toString().endsWith("txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> list =
                    Files.find(path, 2, (p, attr) -> p.toString().endsWith("txt"))
                            .map(Path::toString)
                            .collect(Collectors.toList());
            assertThat(list).containsOnly("src\\main\\java\\text\\memo.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // list
        try {
            Files.list(path).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> list =
                    Files.list(path)
                            .map(Path::toString)
                            .collect(Collectors.toList());
            assertThat(list).containsOnly("src\\main\\java\\text\\memo.txt",
                    "src\\main\\java\\text\\access.log",
                    "src\\main\\java\\text\\csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // walk
        try {
            Files.walk(path).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> list =
                    Files.walk(path)
                            .map(Path::toString)
                            .collect(Collectors.toList());
            assertThat(list).containsOnly("src\\main\\java\\text",
                    "src\\main\\java\\text\\memo.txt",
                    "src\\main\\java\\text\\access.log",
                    "src\\main\\java\\text\\csv",
                    "src\\main\\java\\text\\csv\\data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
