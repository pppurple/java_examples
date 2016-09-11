package javase8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Created by pppurple on 2016/08/22.
 */
public class StreamApiTerminalOperationTest {
    @Test
    public void reduce() {
        int sum = IntStream.rangeClosed(1, 10)
                .reduce(0, (prv, prs) -> prv + prs);
        assertThat(sum).isEqualTo(55);

        String text = Stream.of("aaa", "bbb", "ccc")
                .reduce("", (prv, prs) -> prv + prs);
        assertThat(text).isEqualTo("aaabbbccc");

        Optional<String> text2 = Stream.of("AAA", "BBB", "CCC")
                .reduce((prv, prs) -> prv + prs);
        assertThat(text2.get()).isEqualTo("AAABBBCCC");
    }

    @Test
    public void toList() {
        List<Integer> list = IntStream.rangeClosed(1, 5)
                .map(i -> i + 100)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsSequence(101, 102, 103, 104, 105);

        List<String> text = Stream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertThat(text).containsSequence("AAA", "BBB", "CCC");
    }

    @Test
    public void joining() {
        String text = Stream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .collect(Collectors.joining());
        assertThat(text).isEqualTo("AAABBBCCC");

        String ints = IntStream.rangeClosed(1, 9)
                .mapToObj(i -> String.valueOf(i))
                .collect(Collectors.joining());
        assertThat(ints).isEqualTo("123456789");

        String csv = IntStream.rangeClosed(1, 9)
                .mapToObj(i -> String.valueOf(i))
                .collect(Collectors.joining(","));
        assertThat(csv).isEqualTo("1,2,3,4,5,6,7,8,9");
    }

    @Test
    public void counting() {
        Long count = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.counting());
        assertThat(count).isEqualTo(10);

        Long countStr = Stream.of("a", "b", "c", "d")
                .collect(Collectors.counting());
        assertThat(countStr).isEqualTo(4);
    }

    @Test
    public void maxBy() {
        Integer max = Stream.of(2, 3, 8, 5, 6)
                .collect(Collectors.maxBy(Integer::compare))
                .get();
        assertThat(max).isEqualTo(8);
    }

    @Test
    public void minBy() {
        Integer min = Stream.of(2, 3, 8, 5, 6)
                .collect(Collectors.minBy(Integer::compare))
                .get();
        assertThat(min).isEqualTo(2);
    }

    @Test
    public void summingInt() {
        int sum = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.summingInt(Integer::intValue));
        assertThat(sum).isEqualTo(55);

        int lengthSum = Stream.of("a", "bb", "ccc")
                .collect(Collectors.summingInt(str -> str.length()));
        assertThat(lengthSum).isEqualTo(6);
    }

    @Test
    public void averagingInt() {
        double avg = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.averagingInt(Integer::intValue));
        assertThat(avg).isEqualTo(5.5);

         double lengthAvg = Stream.of("a", "bb", "ccc")
                .collect(Collectors.averagingInt(str -> str.length()));
        assertThat(lengthAvg).isEqualTo(2.0);
    }

    @Test
    public void summarizingInt() {
        IntSummaryStatistics stat = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.summarizingInt(Integer::intValue));
        int max = stat.getMax();
        int min = stat.getMin();
        double sum = stat.getSum();
        double avg = stat.getAverage();
        assertThat(max).isEqualTo(10);
        assertThat(min).isEqualTo(1);
        assertThat(sum).isEqualTo(55);
        assertThat(avg).isEqualTo(5.5);
    }

    @Test
    public void mapping() {
        List<String> upperText = Stream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertThat(upperText).containsSequence("AAA", "BBB", "CCC");

        List<String> upperTextMapping = Stream.of("aaa", "bbb", "ccc")
                .collect(Collectors.mapping(String::toUpperCase,
                        Collectors.toList()));
        assertThat(upperText).containsSequence("AAA", "BBB", "CCC");
    }

    @Test
    public void reducing() {
        int sumReduce = IntStream.rangeClosed(1, 10)
                .reduce(0, (prv, prs) -> prv + prs);
        assertThat(sumReduce).isEqualTo(55);

        int sumReducing = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.reducing(0, (sum, i) -> sum + i));
        assertThat(sumReducing).isEqualTo(55);

        String text = Stream.of("aaa", "bbb", "ccc")
                .collect(Collectors.reducing("", (prv, prs) -> prv + prs));
        assertThat(text).isEqualTo("aaabbbccc");
    }

    @Test
    public void groupingBy() {
        Map<Integer, List<String>> lengthMap =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                .collect(Collectors.groupingBy(String::length));
        assertThat(lengthMap).containsOnlyKeys(1, 2, 3);
        assertThat(lengthMap).contains(entry(1, Arrays.asList("a", "d")));
        assertThat(lengthMap).contains(entry(2, Arrays.asList("bb", "ee")));
        assertThat(lengthMap).contains(entry(3, Arrays.asList("ccc", "fff")));
        assertThat(lengthMap).containsOnly(entry(1, Arrays.asList("a", "d")),
                entry(2, Arrays.asList("bb", "ee")),
                entry(3, Arrays.asList("ccc", "fff")));

        Map<Integer, Long> countLength =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                .collect(Collectors.groupingBy(String::length,
                        Collectors.counting()));
        assertThat(countLength).containsOnly(entry(1, 2L), entry(2, 2L), entry(3, 2L));

        Map<String, Long> stringCount =
                Stream.of("a", "bb", "ccc", "A", "dd", "CCC")
                        .collect(Collectors.groupingBy(String::toUpperCase,
                                () -> new TreeMap<String, Long>(),
                                Collectors.counting()));
        assertThat(stringCount).containsOnlyKeys("A", "BB", "CCC", "DD");
        assertThat(stringCount).containsValues(2L, 1L, 2L, 1L);
    }

    @Test
    public void partitioningBy() {
        Map<Boolean, List<String>> length3 =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.partitioningBy(s -> s.length() > 2));
        assertThat(length3).contains(entry(true, Arrays.asList("ccc", "fff")));
        assertThat(length3).contains(entry(false, Arrays.asList("a", "bb", "d", "ee")));
    }

    @Test
    public void toMap() {
        Map<String, String> upper =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.toMap(s -> s,
                                String::toUpperCase));
        assertThat(upper).contains(entry("a", "A"));
        assertThat(upper).contains(entry("bb", "BB"));
        assertThat(upper).contains(entry("ccc", "CCC"));
    }

    @Test
    public void toSet() {
        Set<String> set = Stream.of("a", "b", "c", "b", "d")
                .collect(Collectors.toSet());
        assertThat(set).containsOnly("a", "b", "c", "d");
    }

    @Test
    public void allMatch() {
        boolean matchAll = IntStream.rangeClosed(1, 10)
                .allMatch(i -> i > 8);
        assertThat(matchAll).isFalse();
    }

    @Test
    public void anyMatch() {
        boolean matchAny = IntStream.rangeClosed(1, 10)
                .anyMatch(i -> i > 8);
        assertThat(matchAny).isTrue();
    }

    @Test
    public void noneMatch() {
        boolean matchNone = IntStream.rangeClosed(1, 10)
                .noneMatch(i -> i > 10);
        assertThat(matchNone).isTrue();
    }

    @Test
    public void findFirst() {
        Optional<String> first = Stream.of("a", "aa", "aaa")
                .filter(s -> s.length() > 2)
                .findFirst();
        assertThat(first.get()).isEqualTo("aaa");
    }

    @Test
    public void findAny() {
        Optional<String> any = Stream.of("a", "aa", "aaa")
                .filter(s -> s.length() > 2)
                .findAny();
        assertThat(any.get()).isEqualTo("aaa");
    }

    @Test
    public void min() {
        OptionalInt min = IntStream.of(3, 5, 2, 8, 4)
                .min();
        assertThat(min.getAsInt()).isEqualTo(2);

        Optional<String> minText = Stream.of("aaa", "bbb", "ccc")
                .min(Comparator.naturalOrder());
        assertThat(minText.get()).isEqualTo("aaa");
    }

    @Test
    public void max() {
        OptionalInt max = IntStream.of(3, 5, 2, 8, 4)
                .max();
        assertThat(max.getAsInt()).isEqualTo(8);

        Optional<String> maxText = Stream.of("aaa", "bbb", "ccc")
                .max(Comparator.naturalOrder());
        assertThat(maxText.get()).isEqualTo("ccc");
    }

    @Test
    public void count() {
        long count = IntStream.rangeClosed(1, 10)
                .filter(i -> i > 5)
                .count();
        assertThat(count).isEqualTo(5);
    }

    @Test
    public void toArray() {
        int[] ints = IntStream.rangeClosed(1, 5)
                .toArray();
        assertThat(ints).containsSequence(1, 2, 3, 4, 5);

        String[] texts = Stream.of("aaa", "bbb", "ccc")
                .toArray(String[]::new);
        assertThat(texts).containsSequence("aaa", "bbb", "ccc");
    }

    @Test
    public void sum() {
        int sum = IntStream.rangeClosed(1, 10)
                .sum();
        assertThat(sum).isEqualTo(55);
    }

    @Test
    public void average() {
        OptionalDouble avg = IntStream.rangeClosed(1, 10)
                .average();
        assertThat(avg.getAsDouble()).isEqualTo(5.5);
    }
}
