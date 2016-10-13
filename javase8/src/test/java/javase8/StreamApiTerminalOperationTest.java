package javase8;

import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class StreamApiTerminalOperationTest {
    @Test
    public void count() {
        long count = IntStream.rangeClosed(1, 10)
                .filter(i -> i > 5)
                .count();
        assertThat(count).isEqualTo(5);
    }

    @Test
    public void min() {
        // int
        OptionalInt min = IntStream.of(3, 5, 2, 8, 4)
                .min();
        assertThat(min.getAsInt()).isEqualTo(2);

        // String
        Optional<String> minText = Stream.of("aaa", "bbb", "ccc")
                .min(Comparator.naturalOrder());
        assertThat(minText.get()).isEqualTo("aaa");
    }

    @Test
    public void max() {
        // int
        OptionalInt max = IntStream.of(3, 5, 2, 8, 4)
                .max();
        assertThat(max.getAsInt()).isEqualTo(8);

        // String
        Optional<String> maxText = Stream.of("aaa", "bbb", "ccc")
                .max(Comparator.naturalOrder());
        assertThat(maxText.get()).isEqualTo("ccc");
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

    @Test
    public void findFirst() {
        Optional<String> first = Stream.of("a", "aa", "aaa")
                .filter(s -> s.length() > 1)
                .findFirst();
        assertThat(first.get()).isEqualTo("aa");
    }

    static List<String> aList = Arrays.asList("aa", "aaa");
    Condition<String> aCondtion = new Condition<>(aList::contains, "a match");

    @Test
    public void findAny() {
        Optional<String> any = Stream.of("a", "aa", "aaa")
                .filter(s -> s.length() > 1)
                .findAny();
        assertThat(any.get()).is(aCondtion);
    }

    @Test
    public void anyMatch() {
        boolean matchAny = IntStream.rangeClosed(1, 10)
                .anyMatch(i -> i > 8);
        assertThat(matchAny).isTrue();

        boolean matchAny2 = IntStream.rangeClosed(1, 10)
                .anyMatch(i -> i > 10);
        assertThat(matchAny2).isFalse();
    }

    @Test
    public void allMatch() {
        boolean matchAll = IntStream.rangeClosed(1, 10)
                .allMatch(i -> i > 0);
        assertThat(matchAll).isTrue();

        boolean matchAll2 = IntStream.rangeClosed(1, 10)
                .allMatch(i -> i > 8);
        assertThat(matchAll2).isFalse();
    }

    @Test
    public void noneMatch() {
        boolean matchNone = IntStream.rangeClosed(1, 10)
                .noneMatch(i -> i > 10);
        assertThat(matchNone).isTrue();

        boolean matchNone2 = IntStream.rangeClosed(1, 10)
                .noneMatch(i -> i > 8);
        assertThat(matchNone2).isFalse();
    }

    @Test
    public void reduceWithIdentity() {
        // reduce(T identity, BinaryOperator<T> accumulator)
        int sum = IntStream.rangeClosed(1, 10)
                .reduce(1, (a, b) -> a + b);
        assertThat(sum).isEqualTo(56);

        String text = Stream.of("aaa", "bbb", "ccc")
                .reduce("#", (a, b) -> a + b);
        assertThat(text).isEqualTo("#aaabbbccc");
    }

    @Test
    public void reduce() {
        // reduce(BinaryOperator<T> accumulator)
        OptionalInt sum = IntStream.rangeClosed(1, 10)
                .reduce((a, b) -> a + b);
        assertThat(sum.getAsInt()).isEqualTo(55);

        Optional<String> text = Stream.of("aaa", "bbb", "ccc")
                .reduce((a, b) -> a + b);
        assertThat(text.get()).isEqualTo("aaabbbccc");
    }

    @Test
    public void reduceWithCombiner() {
        // reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
        Map<Integer, String> map = Stream.of("a", "bb", "ccc", "dd")
                .reduce(new HashMap<>(),
                        (m, s) -> {
                            m.put(s.length(), s);
                            return m;
                        },
                        (m1, m2) -> {
                            m1.putAll(m2);
                            return m1;
                        });

        map.keySet().forEach(k -> System.out.println(k + ":" + map.get(k)));

        assertThat(map).containsOnly(entry(1, "a"),
                entry(2, "dd"),
                entry(3, "ccc"));
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
    public void collect() {
        Map<Integer, String> map = Stream.of("a", "bb", "ccc", "dd")
                .collect(HashMap::new,
                        (m, s) -> m.put(s.length(), s),
                        HashMap::putAll);

        assertThat(map).containsOnly(entry(1, "a"),
                entry(2, "dd"),
                entry(3, "ccc"));
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
    public void minBy() {
        Integer min = Stream.of(2, 3, 8, 5, 6)
                .collect(Collectors.minBy(Integer::compare))
                .get();
        assertThat(min).isEqualTo(2);
    }

    @Test
    public void maxBy() {
        Integer max = Stream.of(2, 3, 8, 5, 6)
                .collect(Collectors.maxBy(Integer::compare))
                .get();
        assertThat(max).isEqualTo(8);
    }

    @Test
    public void summingInt() {
        int sum = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.summingInt(Integer::intValue));
        assertThat(sum).isEqualTo(55);

        int lengthSum = Stream.of("a", "bb", "ccc")
                .collect(Collectors.summingInt(String::length));
        assertThat(lengthSum).isEqualTo(6);
    }

    @Test
    public void averagingInt() {
        double avg = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.averagingInt(Integer::intValue));
        assertThat(avg).isEqualTo(5.5);

        double lengthAvg = Stream.of("a", "bb", "ccc")
                .collect(Collectors.averagingInt(String::length));
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
        long count = stat.getCount();
        assertThat(max).isEqualTo(10);
        assertThat(min).isEqualTo(1);
        assertThat(sum).isEqualTo(55);
        assertThat(avg).isEqualTo(5.5);
        assertThat(count).isEqualTo(10);
    }

    @Test
    public void joining() {
        // String
        String text = Stream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .collect(Collectors.joining());
        assertThat(text).isEqualTo("AAABBBCCC");

        // int
        String ints = IntStream.rangeClosed(1, 9)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        assertThat(ints).isEqualTo("123456789");
    }

    @Test
    public void joiningWithDelimiter() {
        String csv = IntStream.rangeClosed(1, 9)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
        assertThat(csv).isEqualTo("1,2,3,4,5,6,7,8,9");
    }

    @Test
    public void joiningWithPrefixAndSuffix() {
        String csv = IntStream.rangeClosed(1, 9)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
        assertThat(csv).isEqualTo("[1,2,3,4,5,6,7,8,9]");
    }

    @Test
    public void mapping() {
        // Collectors.mapping
        List<String> upperTextMapping = Stream.of("aaa", "bbb", "ccc")
                .collect(Collectors.mapping(String::toUpperCase,
                        Collectors.toList()));
        assertThat(upperTextMapping).containsSequence("AAA", "BBB", "CCC");

        // map()
        List<String> upperText = Stream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertThat(upperText).containsSequence("AAA", "BBB", "CCC");
    }

    @Test
    public void reducingWithIdentity() {
        // Collectors.reducing(T identity, BinaryOperator<T> op)
        int sumReducing = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.reducing(1, (sum, i) -> sum + i));
        assertThat(sumReducing).isEqualTo(56);

        String text = Stream.of("aaa", "bbb", "ccc")
                .collect(Collectors.reducing("#", (a, b) -> a + b));
        assertThat(text).isEqualTo("#aaabbbccc");
    }

    @Test
    public void reducing() {
        // Collectors.reducing(BinaryOperator<T> op)
        Optional<String> text = Stream.of("aaa", "bbb", "ccc")
                .collect(Collectors.reducing((a, b) -> a + b));
        assertThat(text.get()).isEqualTo("aaabbbccc");
    }

    @Test
    public void reducingWithMapper() {
        // Collectors.reducing(U identity, Function<? super T,? extends U> mapper, BinaryOperator<U> op)
        int sumLength = Stream.of("a", "bb", "ccc", "dd")
                .collect(Collectors.reducing(100,
                        String::length,
                        (a, b) -> a + b));

        assertThat(sumLength).isEqualTo(108);
    }

    @Test
    public void groupingBy() {
        // Collectors.groupingBy(Function<? super T,? extends K> classifier)
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
    }

    @Test
    public void groupingByWithDownstream() {
        // Collectors.groupingBy(Function<? super T,? extends K> classifier,
        //                       Collector<? super T,A,D> downstream)
        Map<Integer, Long> countLength =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.groupingBy(String::length,
                                Collectors.counting()));
        assertThat(countLength).containsOnly(entry(1, 2L), entry(2, 2L), entry(3, 2L));
    }

    @Test
    public void groupingByWithMapFactory() {
        // Collectors.groupingBy(Function<? super T,? extends K> classifier,
        //                       Supplier<M> mapFactory,
        //                       Collector<? super T,A,D> downstream)
        Map<String, Long> stringCount =
                Stream.of("a", "bb", "ccc", "A", "dd", "CCC")
                        .collect(Collectors.groupingBy(String::toUpperCase,
                                TreeMap::new,
                                Collectors.counting()));
        assertThat(stringCount).containsOnlyKeys("A", "BB", "CCC", "DD");
        assertThat(stringCount).containsValues(2L, 1L, 2L, 1L);
    }

    // FIXME: 2016/10/14
    @Test
    public void groupingByConcurrent() {
        // groupingByConcurrent(Function<? super T,? extends K> classifier,
        //                      Collector<? super T,A,D> downstream)
        ConcurrentMap<Integer, List<String>> lengthMap =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.groupingByConcurrent(String::length));
        assertThat(lengthMap).containsOnlyKeys(1, 2, 3);
        assertThat(lengthMap).contains(entry(1, Arrays.asList("a", "d")));
        assertThat(lengthMap).contains(entry(2, Arrays.asList("bb", "ee")));
        assertThat(lengthMap).contains(entry(3, Arrays.asList("ccc", "fff")));
        assertThat(lengthMap).containsOnly(entry(1, Arrays.asList("a", "d")),
                entry(2, Arrays.asList("bb", "ee")),
                entry(3, Arrays.asList("ccc", "fff")));
    }

    // FIXME: 2016/10/14
    @Test
    public void groupingByConcurrentWithDownstream() {
        ConcurrentMap<Integer, Long> countLength =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.groupingByConcurrent(String::length,
                                Collectors.counting()));
        assertThat(countLength).containsOnly(entry(1, 2L), entry(2, 2L), entry(3, 2L));
    }

    // // FIXME: 2016/10/14
    @Test
    public void groupingByConcurrentWithMapFactory() {
        ConcurrentMap<String, Long> stringCount =
                Stream.of("a", "bb", "ccc", "A", "dd", "CCC")
                        .collect(Collectors.groupingByConcurrent(String::toUpperCase,
                                ConcurrentSkipListMap::new,
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
    public void partitioningByWithDownstream() {
        Map<Boolean, Long> length3 =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.partitioningBy(s -> s.length() > 2,
                                Collectors.counting()));
        assertThat(length3).contains(entry(true, 2L));
        assertThat(length3).contains(entry(false, 4L));
    }

    // FIXME: 2016/10/14 
    @Test
    public void collectingAndThen() {
        List<String> list = Stream.of("a", "b", "c", "d", "e")
                .map(String::toUpperCase)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        assertThat(list).containsOnly("A", "B", "C", "D", "E");
    }

    // FIXME: 2016/10/14 
    @Test
    public void toCollection() {
        
    }
    
    @Test
    public void toList() {
        // int
        List<Integer> list = IntStream.rangeClosed(1, 5)
                .map(i -> i + 100)
                .boxed()
                .collect(Collectors.toList());
        assertThat(list).containsSequence(101, 102, 103, 104, 105);

        // String
        List<String> text = Stream.of("aaa", "bbb", "ccc")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertThat(text).containsSequence("AAA", "BBB", "CCC");
    }

    @Test
    public void toMap() {
        // Collectors.toMap(Function<? super T,? extends K> keyMapper,
        //                  Function<? super T,? extends U> valueMapper)
        Map<String, String> upper =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.toMap(s -> s,
                                String::toUpperCase));
        assertThat(upper).contains(entry("a", "A"));
        assertThat(upper).contains(entry("bb", "BB"));
        assertThat(upper).contains(entry("ccc", "CCC"));
        assertThat(upper).contains(entry("d", "D"));
        assertThat(upper).contains(entry("ee", "EE"));
        assertThat(upper).contains(entry("fff", "FFF"));
    }

    @Test
    public void toMapWithMerge() {
        // Collectors.toMap(Function<? super T,? extends K> keyMapper,
        //                  Function<? super T,? extends U> valueMapper,
        //                  BinaryOperator<U> mergeFunction)
        Map<Integer, String> upper =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.toMap(String::length,
                                s -> s,
                                (s1, s2) -> s1 + "," + s2));
        assertThat(upper).contains(entry(1, "a,d"));
        assertThat(upper).contains(entry(2, "bb,ee"));
        assertThat(upper).contains(entry(3, "ccc,fff"));
    }

    @Test
    public void toMapWithMapSupplier() {
        // Collectors.toMap(Function<? super T,? extends K> keyMapper,
        //                  Function<? super T,? extends U> valueMapper,
        //                  BinaryOperator<U> mergeFunction,
        //                  Supplier<M> mapSupplier)
        Map<Integer, String> upper =
                Stream.of("a", "bb", "ccc", "d", "ee", "fff")
                        .collect(Collectors.toMap(String::length,
                                s -> s,
                                (s1, s2) -> s1 + "," + s2,
                                HashMap::new));
        assertThat(upper).contains(entry(1, "a,d"));
        assertThat(upper).contains(entry(2, "bb,ee"));
        assertThat(upper).contains(entry(3, "ccc,fff"));
    }

    // FIXME: 2016/10/14 
    @Test
    public void toConcurrentMap() {
        
    }

    // FIXME: 2016/10/14
    @Test
    public void toConcurrentMapWithMerge() {

    }

    // FIXME: 2016/10/14
    @Test
    public void toConcurrentMapWithMapSupplier() {

    }

    @Test
    public void toSet() {
        Set<String> set = Stream.of("a", "b", "c", "b", "d")
                .collect(Collectors.toSet());
        assertThat(set).containsOnly("a", "b", "c", "d");
    }
}
