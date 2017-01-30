package multithread.introduction;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MyThreadFactoryMain {
    public static void main(String[] args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        Thread thread = factory.newThread(new MyRunnable());
        thread.start();
    }
}
