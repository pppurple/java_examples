package javase8;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamSortWithMultiProperties {

    private List<Person> persons = new ArrayList<>();
    private Person Anna;
    private Person Bobby;
    private Person Bob;
    private Person David;

    @Before
    public void setUp() {
        Anna = new Person("Anna", 24);
        Bobby = new Person("Bobby", 42);
        Bob = new Person("Bobby", 30);
        David = new Person("David", 33);
        persons = Arrays.asList(Anna, Bobby, Bob, David);
    }

    @Test
    public void nameで昇順でsort() {
        List<Person> sorted = persons.stream()
                .sorted(Comparator.comparing(Person::getName))
                .collect(Collectors.toList());
        assertThat(sorted).containsSubsequence(Anna, Bob, David);
        assertThat(sorted).containsSubsequence(Anna, Bobby, David);
    }

    @Test
    public void nameで昇順でsort2() {
        List<Person> sorted = persons.stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .collect(Collectors.toList());
        assertThat(sorted).containsSubsequence(Anna, Bob, David);
        assertThat(sorted).containsSubsequence(Anna, Bobby, David);
    }

    @Test
    public void nameで昇順でsort3() {
        List<Person> sorted = persons.stream()
                .sorted(comparatorWithName)
                .collect(Collectors.toList());
        assertThat(sorted).containsSubsequence(Anna, Bob, David);
        assertThat(sorted).containsSubsequence(Anna, Bobby, David);
    }

    @Test
    public void nameで降順でsort() {
        List<Person> sorted = persons.stream()
                .sorted(Comparator.comparing(Person::getName).reversed())
                .collect(Collectors.toList());
        assertThat(sorted).containsSubsequence(David, Bob, Anna);
        assertThat(sorted).containsSubsequence(David, Bobby, Anna);
    }

    private Comparator<Person> comparatorWithName = (p1, p2) -> {
        return p1.getName().compareTo(p2.getName());
    };

    @Value
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class Person {
        String name;
        int age;
    }
}
