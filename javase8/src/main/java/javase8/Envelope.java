package javase8;

import java.util.function.Consumer;

/**
 * Created by pppurple on 2016/08/13.
 */
public class Envelope<T> {
    private T contents;

    private Envelope(T contents) {
        this.contents = contents;
    }

    public static <S> Envelope<S> create(S contents) {
        return new Envelope<S>(contents);
    }

    public void print(Consumer<T> consumer) {
        consumer.accept(contents);
    }
}
