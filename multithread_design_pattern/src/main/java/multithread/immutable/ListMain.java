package multithread.immutable;

import java.util.ArrayList;
import java.util.List;

public class ListMain {
    public static void main(String[] args) {
        final List<Integer> list = new ArrayList<>();
        new Thread(new User(list)).start();
        new Thread(new User(list)).start();
        new Thread(new User(list)).start();
    }

    static class User implements Runnable {
        private final List<Integer> list;

        public User(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000000; i++) {
                list.add(1);
                list.get(0);
                list.remove(0);
            }
        }
    }
}
