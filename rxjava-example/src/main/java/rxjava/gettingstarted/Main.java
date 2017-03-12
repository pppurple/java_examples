package rxjava.gettingstarted;

import io.reactivex.Flowable;

public class Main {
    public static void main(String[] args) {
        Flowable.just("Hello World").subscribe(System.out::println);
    }
}
