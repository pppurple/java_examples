package multithread.thread_per_message.with_concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static void main(String[] args) {
        // Thread
        new Thread(Main::doSomething).start();

        // Runnable
        new Thread(new Runnable() {
            @Override
            public void run() {
                doSomething();
            }
        }).start();

        // ThreadFactory
        ThreadFactory factory = newInstance();
        factory.newThread(new Runnable() {
            @Override
            public void run() {
                doSomething();
            }
        }).start();

        // Executors.defaultThreadFactory
        ThreadFactory factory2 = Executors.defaultThreadFactory();
        factory2.newThread(new Runnable() {
            @Override
            public void run() {
                doSomething();
            }
        }).start();
    }

    private static void doSomething() {
        // do something
    }

    private static ThreadFactory newInstance() {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
    }
}
