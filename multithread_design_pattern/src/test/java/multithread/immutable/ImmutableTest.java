package multithread.immutable;

import com.google.common.collect.ImmutableList;
import multithread.immutable.fortest.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutableTest {
    @Test
    public void personTest() {
        final Person person = new Person("Anne", "Japan", 28);
        // error
        // person.setName("Bobby");
        // person.name("Cindy");
    }

    @Test
    public void personHasMutableTest() {
        final MyMutable mutable = new MyMutable("aaa", 111);
        final PersonHasMutable personHasMutable = new PersonHasMutable("Anne", "Japan", 28, mutable);

        // mutable更新
        mutable.setMessage("bbb");
        mutable.setCode(200);

        assertThat(personHasMutable.getMyMutable().getMessage()).isEqualTo("aaa");
        assertThat(personHasMutable.getMyMutable().getCode()).isEqualTo(111);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unmodifiableListTest() throws UnsupportedOperationException {
        final List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        final List<String> unmodified = Collections.unmodifiableList(list);

        // addの操作自体はコンパイルが通る
        // 実行時にUnsupportedOperationExceptionが発生する
        unmodified.add("ccc");
    }

    @Test
    public void unmodifiableListHasMutableTest() throws UnsupportedOperationException {
        final List<MyMutable> list = new ArrayList<>();
        MyMutable myMutable1 = new MyMutable("aaa", 111);
        MyMutable myMutable2 = new MyMutable("bbb", 222);
        list.add(myMutable1);
        list.add(myMutable2);
        final List<MyMutable> unmodified = Collections.unmodifiableList(list);

        // 参照の中身は変更可能
        myMutable1.setMessage("ccc");

        assertThat(unmodified.get(0).getMessage()).isEqualTo("ccc");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableListTest() throws UnsupportedOperationException {
        final List<String> immutableList = ImmutableList.of("aaa", "bbb");

        final List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        final List<String> immutableList2 = ImmutableList.copyOf(list);

        // addの操作自体はコンパイルが通る
        // 実行時にUnsupportedOperationExceptionが発生する
        immutableList.add("ccc");
    }

    @Test
    public void immutableListHasMutableTest() {
        final List<MyMutable> list = new ArrayList<>();
        MyMutable myMutable1 = new MyMutable("aaa", 111);
        MyMutable myMutable2 = new MyMutable("bbb", 222);
        list.add(myMutable1);
        list.add(myMutable2);

        final List<MyMutable> immutableList = ImmutableList.copyOf(list);

        // 参照の中身は変更可能
        myMutable1.setMessage("ccc");

        assertThat(immutableList.get(0).getMessage()).isEqualTo("ccc");
    }

    @Test
    public void lombokValueTest() {
        PersonWithValue person = new PersonWithValue("anna", "Japan", 28);

        // error
        // person.setName("Bobby");
        // person.name("Cindy");
    }

    @Test
    public void lombokValueBuilderTest() {
        PersonWithBuilder person = PersonWithBuilder
                .builder()
                .name("anna")
                .country("Japan")
                .age(28)
                .build();

        // error
        // person.setName("Bobby");
        // person.name("Cindy");
    }
}
