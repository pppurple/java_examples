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
        Runnable addTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                atomicInt.incrementAndGet();
            }
        };
        new Thread(addTask).start();
        new Thread(addTask).start();

        Thread.sleep(5_000L);

        System.out.println(atomicInt.get());
    }

    @Ignore
    @Test
    public void notAtomicIncrementTest() throws InterruptedException {
        Runnable addTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                notAtomicInt++;
            }
        };
        new Thread(addTask).start();
        new Thread(addTask).start();

        Thread.sleep(5_000L);

        System.out.println(notAtomicInt);
    }

    @Ignore
    @Test
    public void compareAndSetTest() throws InterruptedException {
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

    @Ignore
    @Test
    public void notAtomicUpdateTest() throws InterruptedException {
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

    @Ignore
    @Test
    public void updateAndGetTest() throws InterruptedException {
        Runnable updateTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                atomicInt.updateAndGet(current -> current + 2);
            }
        };

        new Thread(updateTask).start();
        new Thread(updateTask).start();

        Thread.sleep(5_000L);

        System.out.println(atomicInt.get());
    }

    @Test
    public void accumulateAndGetTest() throws InterruptedException {
        Runnable updateTask = () -> {
            for (int i = 0; i < 50_000; i++) {
                atomicInt.accumulateAndGet(atomicInt.get(), (current, updated) -> current + 2);
            }
        };

        new Thread(updateTask).start();
        new Thread(updateTask).start();

        Thread.sleep(5_000L);

        System.out.println(atomicInt.get());
    }
}
