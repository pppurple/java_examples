package javase8;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    private static AtomicInteger atomicInt = new AtomicInteger();
    private static int notAtomicInt = 1;

    @Ignore
    @Test
    public void atomicIncrementTest() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();

        Runnable addTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                list.add(atomicInt.incrementAndGet());
            }
        };
        new Thread(addTask).start();
        new Thread(addTask).start();

        Thread.sleep(5_000L);

        Collections.sort(list);

        list.forEach(System.out::println);
    }

    @Ignore
    @Test
    public void notAtomicIncrementTest() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();

        Runnable addTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                list.add(notAtomicInt++);
            }
        };
        new Thread(addTask).start();
        new Thread(addTask).start();

        Thread.sleep(5_000L);

        Collections.sort(list);

        list.forEach(System.out::println);
    }

    @Test
    public void atomicCompareAndSetTest() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();

        Runnable updateTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                int current = atomicInt.get();
                while (!atomicInt.compareAndSet(current, current + 2));
            }
        };

        new Thread(updateTask).start();
        new Thread(updateTask).start();

        Thread.sleep(5_000L);

        System.out.println(atomicInt.get());
    }

    @Test
    public void notAtomicCompareAndSetTest() throws InterruptedException {
        Runnable updateTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                notAtomicInt = notAtomicInt + 2;
            }
        };

        new Thread(updateTask).start();
        new Thread(updateTask).start();

        Thread.sleep(5_000L);

        System.out.println(notAtomicInt);
    }

}
