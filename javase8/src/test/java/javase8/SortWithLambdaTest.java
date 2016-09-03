package javase8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;


/**
 * Created by pppurple on 2016/08/13.
 */
public class SortWithLambdaTest {
    @Test
    public void リストのソート() {
        List<Integer> list = Arrays.asList(100, 150, 200);

        // 昇順ソート
        Collections.sort(list, (x, y) -> Integer.compare(x, y));
        list.forEach(System.out::println);
        assertThat(list, is(contains(100, 150, 200)));
        // 降順ソート
        Collections.sort(list, (x, y) -> Integer.compare(y, x));
        list.forEach(System.out::println);
        assertThat(list, is(contains(200, 150, 100)));
    }

    <T> Comparator<T> reverse(Comparator<T> comparator) {
        return (x, y) -> -comparator.compare(x, y);
    }

    @Test
    public void reverseの使用() {
        List<Integer> list = Arrays.asList(100, 150, 200);

        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

        // 昇順ソート
        Collections.sort(list, comparator);
        list.forEach(System.out::println);
        assertThat(list, is(contains(100, 150, 200)));
        // 降順ソート
        Collections.sort(list, reverse(comparator));
        list.forEach(System.out::println);
        assertThat(list, is(contains(200, 150, 100)));
    }

    class Person {
        private String first;
        private String last;

        public Person(String first, String last) {
            this.first = first;
            this.last = last;
        }

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public String toString() {
            return first + " " + last;
        }
    }

    // 一つのコンパレータで比較するコンパレータ
    Comparator<Person> comparator = (p1, p2) -> {
        // 姓で比較
        String last1 = p1.getLast();
        String last2 = p2.getLast();
        int result = last1.compareTo(last2);

        if (result != 0) {
            return result;
        } else {
            // 姓が同じ場合、名で比較
            String first1 = p1.getFirst();
            String first2 = p2.getFirst();
            return first1.compareTo(first2);
        }
    };

    @Test
    public void 一つのコンパレータで比較() {
        Person Larry = new Person("Larry", "Wall");
        Person Tim = new Person("Tim", "Chris");
        Person Tom = new Person("Tom", "Chris");

        List<Person> list = Arrays.asList(Larry, Tim, Tom);

        Collections.sort(list, comparator);
        list.forEach(System.out::println);
        assertThat(list, is(contains(Tim, Tom, Larry)));
    }

    // 複合コンパレータ
    Comparator<Person> lastNameComparator = (p1, p2) -> {
        String last1 = p1.getLast();
        String last2 = p2.getLast();
        return last1.compareTo(last2);
    };

    Comparator<Person> firstNameComparator = (p1, p2) -> {
        String first1 = p1.getFirst();
        String first2 = p2.getFirst();
        return first1.compareTo(first2);
    };

    // 姓で比較した後、名で比較
    Comparator<Person> compositeComparator = lastNameComparator.thenComparing(firstNameComparator);

    @Test
    public void 複合コンパレータで比較() {
        Person Larry = new Person("Larry", "Wall");
        Person Tim = new Person("Tim", "Chris");
        Person Tom = new Person("Tom", "Chris");

        List<Person> list = Arrays.asList(Larry, Tim, Tom);

        Collections.sort(list, compositeComparator);
        list.forEach(System.out::println);
        assertThat(list, is(contains(Tim, Tom, Larry)));
    }
}
