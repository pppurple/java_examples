package javase8;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamApiDistinctTest {

    private List<Person> persons;
    private Person annie;
    private Person bobby;
    private Person cindy;
    private Person danny;
    private Person anny;

    {
        annie= new Person("Annie", "America", 42);
        bobby = new Person("Bobby", "Japan", 34);
        cindy  = new Person("Cindy", "America", 22);
        danny  = new Person("Danny", "Brazil", 22);
        anny  = new Person("Annie", "America", 42);
        persons = Arrays.asList(annie, bobby, cindy, danny, anny);
    }

    @Test
    public void streamApiDistinctTest() {
        List<Person> distinct = persons.stream()
                .distinct()
                .collect(Collectors.toList());

        assertThat(distinct).containsExactlyInAnyOrder(annie, bobby, cindy, danny);
    }

    @Test
    public void distinctByPropertyTest() {
        Map<String, Boolean> seenCountry = new HashMap<>();
        List<Person> distinctByCountry = persons.stream()
                .filter(p -> seenCountry.putIfAbsent(p.country, Boolean.TRUE) == null)
                .collect(Collectors.toList());
        assertThat(distinctByCountry).containsExactlyInAnyOrder(annie, bobby, danny);

        Map<Integer, Boolean> seenAge = new HashMap<>();
        List<Person> distinctByAge = persons.stream()
                .filter(p -> seenAge.putIfAbsent(p.age, Boolean.TRUE) == null)
                .collect(Collectors.toList());
        assertThat(distinctByAge).containsExactlyInAnyOrder(annie, bobby, cindy);
    }

    @Test
    public void distinctByKeyTest() {
        List<Person> distinctByCountry = persons.stream()
                .filter(distinctByKey(p -> p.country))
                .collect(Collectors.toList());
        assertThat(distinctByCountry).containsExactlyInAnyOrder(annie, bobby, danny);

        List<Person> distinctByAge = persons.stream()
                .filter(distinctByKey(p -> p.age))
                .collect(Collectors.toList());
        assertThat(distinctByAge).containsExactlyInAnyOrder(annie, bobby, cindy);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Value
    @AllArgsConstructor
    private static class Person {
        String name;
        String country;
        int age;
    }
}
