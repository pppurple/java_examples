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
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by pppurple on 2016/08/14.
 */
public class CreateStreamTest {
    @Test
    public void リストからのストリーム処理() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");

        list.stream().forEach(System.out::println);
    }

    @Test
    public void 配列からのストリーム処理() {
        String[] array = {"aaa", "bbb", "ccc", "eee", "fff"};
        Arrays.stream(array).forEach(System.out::println);
        Arrays.stream(array, 2, 4).forEach(System.out::println);

        int[] intArray = {100, 200, 300, 400, 500};
        Arrays.stream(intArray).forEach(System.out::println);
    }

    @Test
    public void ファイル読み込みのストリーム処理() {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/java/javase8/memo.txt"))) {
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path path = Paths.get("src/main/java/javase8/memo.txt");
        try(Stream<String> stream = Files.lines(path)) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ofによるストリーム生成() {
        Stream.of("aaa", "bbb", "ccc").forEach(System.out::println);
        Stream.of(100, 200, 300).forEach(System.out::println);
        String[] array = {"AAA", "BBB", "CCC"};
        Stream.of(array).forEach(System.out::println);
    }

    @Test
    public void rangeによるストリーム生成() {
        // 0から9まで出力
        IntStream.range(0, 10).forEach(System.out::println);
        // 0から10まで出力
        IntStream.rangeClosed(0, 10).forEach(System.out::println);
    }

    @Test
    public void iterateによるストリーム生成() {
        IntStream.iterate(0, i -> i + 100)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void generateによるストリーム生成() {
        Stream.generate(() -> "abc")
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void emptyによるストリーム生成() {
        IntStream.empty().forEach(System.out::println);
    }

    @Test
    public void concatによるストリーム生成() {
        IntStream stream1 = IntStream.range(0, 5);
        IntStream stream2 = IntStream.range(5, 10);
        IntStream.concat(stream1, stream2).forEach(System.out::println);
    }

    @Test
    public void Randomからのストリーム生成() {
        Random random = new Random();
        random.ints(10, 1, 1000).forEach(System.out::println);
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
