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
        Anna = new Person("Anna", "Canada", 24);
        Bobby = new Person("Bobby", "Brazil", 42);
        Bob = new Person("Bobby", "America", 30);
        David = new Person("David", "America", 33);
        persons = Arrays.asList(Anna, Bobby, Bob, David);
    }

    @Test
    public void nameで昇順でsort_compareTo() {
        List<Person> sorted = persons.stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .collect(Collectors.toList());
        assertThat(sorted).containsSubsequence(Anna, Bob, David);
        assertThat(sorted).containsSubsequence(Anna, Bobby, David);
    }

    @Test
    public void nameで昇順でsort_comparing() {
        List<Person> sorted = persons.stream()
                .sorted(Comparator.comparing(Person::getName))
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

    @Test
    public void nameで降順でsort2() {
        List<Person> sorted = persons.stream()
                .sorted(Comparator.comparing(Person::getName, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        assertThat(sorted).containsSubsequence(David, Bob, Anna);
        assertThat(sorted).containsSubsequence(David, Bobby, Anna);
    }

    @Test
    public void nameで昇順_countryで昇順でsort() {
        List<Person> sorted = persons.stream()
                .sorted(comparatorWithNameAndAge)
                .collect(Collectors.toList());
        assertThat(sorted).containsExactly(Anna, Bob, Bobby, David);
    }

    @Test
    public void nameで昇順_countryで昇順でsort_関数合成() {
        List<Person> sorted = persons.stream()
                .sorted(comparatorWithFunctionSynthesis)
                .collect(Collectors.toList());
        assertThat(sorted).containsExactly(Anna, Bob, Bobby, David);
    }

    @Test
    public void nameで昇順_countryで降順でsort_関数合成() {
        List<Person> sorted = persons.stream()
                .sorted(compareWithName.thenComparing(compareWithCountry.reversed()))
                .collect(Collectors.toList());
        assertThat(sorted).containsExactly(Anna, Bobby, Bob, David);
    }

    private Comparator<Person> comparatorWithNameAndAge = (p1, p2) -> {
        int result = p1.getName().compareTo(p2.getName());
        if (result != 0) {
            return result;
        }
        return p1.getCountry().compareTo(p2.getCountry());
    };

    private Comparator<Person> compareWithName = Comparator.comparing(Person::getName);

    private Comparator<Person> compareWithCountry = Comparator.comparing(Person::getCountry);

    // 関数合成
    private Comparator<Person> comparatorWithFunctionSynthesis
            = compareWithName.thenComparing(compareWithCountry);

    @Value
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class Person {
        String name;
        String country;
        int age;
    }
}
