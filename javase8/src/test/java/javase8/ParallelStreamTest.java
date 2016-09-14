package javase8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

/**
 * Created by pppurple on 2016/09/11.
 */
public class ParallelStreamTest {
    @Test
    public void parallelStream() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        // parallel
        System.out.println("[parallel]");
        list.parallelStream().map(String::toUpperCase).forEach(System.out::println);
        // serial
        System.out.println("[serial]");
        list.stream().map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void serialToParallel() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        Stream<String> serial = list.stream();
        Stream<String> parallel = serial.parallel();

        assertThat(parallel.isParallel()).isEqualTo(true);

        // parallel
        System.out.println("[parallel]");
        parallel.map(String::toUpperCase).forEach(System.out::println);
        // serial
        System.out.println("[serial]");
        list.stream().map(String::toUpperCase).forEach(System.out::println);

        // java.lang.IllegalStateException: stream has already been operated upon or closed
        // serial.map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void parallelToSerial() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        Stream<String> parallel = list.parallelStream();
        Stream<String> serial = parallel.sequential();

        assertThat(serial.isParallel()).isEqualTo(false);

        // parallel
        System.out.println("[parallel]");
        list.parallelStream().map(String::toUpperCase).forEach(System.out::println);
        // serial
        System.out.println("[serial]");
        serial.map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void parallelWithOrder() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

        Stream<String> parallel = list.parallelStream();
        Stream<String> parallelWithOrder = list.parallelStream();

        assertThat(parallel.isParallel()).isEqualTo(true);
        assertThat(parallelWithOrder.isParallel()).isEqualTo(true);

        // parallel
        System.out.println("[parallel]");
        parallel.map(String::toUpperCase).forEach(System.out::println);
        // parallel with order
        System.out.println("[order]");
        parallelWithOrder.map(String::toUpperCase).forEachOrdered(System.out::println);
    }

    @Test
    public void groupingByConcurrent() {
        List<String> list = Arrays.asList("a", "bb", "ccc", "d", "ee", "fff");

        ConcurrentMap<Integer, List<String>> map = list.parallelStream().
                collect(Collectors.groupingByConcurrent(String::length));
        assertThat(map).containsOnlyKeys(1, 2, 3);
        assertThat(map.get(1)).containsOnly("a", "d");
        assertThat(map.get(2)).containsOnly("bb", "ee");
        assertThat(map.get(3)).containsOnly("ccc", "fff");
    }

    @Test
    public void toConcurrentMap() {
        List<String> list = Arrays.asList("a", "bb", "ccc", "d", "ee", "fff");

        ConcurrentMap<Integer, List<String>> map = list.parallelStream().
                collect(Collectors.toConcurrentMap(s -> s.length(),
                        s -> Arrays.asList(s),
                        (s1, s2) -> Stream.concat(s1.stream(), s2.stream()).collect(Collectors.toList())));
        assertThat(map).containsOnlyKeys(1, 2, 3);
        assertThat(map.get(1)).containsOnly("a", "d");
        assertThat(map.get(2)).containsOnly("bb", "ee");
        assertThat(map.get(3)).containsOnly("ccc", "fff");
    }

    @Test
    public void performance() {
        int sumSerial = IntStream.rangeClosed(1, 100_000_000).sum();
        System.out.println("serial:" + sumSerial);

    }
}

