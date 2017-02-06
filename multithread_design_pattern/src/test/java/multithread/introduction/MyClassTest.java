package multithread.introduction;

import org.junit.Test;

public class MyClassTest {
    @Test
    public void 同時実行可_インスタンスメソッド() throws InterruptedException {
        MyClass clazz = new MyClass();
        Runnable task = clazz::myInstanceMethod;

        new Thread(task).start();
        new Thread(task).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行可_インスタンスメソッドとsynchronizedメソッド() throws InterruptedException {
        MyClass clazz = new MyClass();
        Runnable taskNonSync = clazz::myInstanceMethod;
        Runnable taskSync = clazz::syncInstanceMethodA;

        new Thread(taskNonSync).start();
        new Thread(taskSync).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行不可_synchronizedメソッド() throws InterruptedException {
        MyClass clazz = new MyClass();
        Runnable taskSync = clazz::syncInstanceMethodA;

        new Thread(taskSync).start();
        new Thread(taskSync).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行不可_異なるsynchronizedメソッド() throws InterruptedException {
        MyClass clazz = new MyClass();
        Runnable taskSyncA = clazz::syncInstanceMethodA;
        Runnable taskSyncB = clazz::syncInstanceMethodB;

        new Thread(taskSyncA).start();
        new Thread(taskSyncB).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行可_異なるインスタンスのsynchronizedメソッド() throws InterruptedException {
        MyClass clazz1 = new MyClass();
        MyClass clazz2 = new MyClass();
        Runnable taskClazz1 = clazz1::syncInstanceMethodA;
        Runnable taskClazz2 = clazz2::syncInstanceMethodA;

        new Thread(taskClazz1).start();
        new Thread(taskClazz2).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行可_異なるインスタンスの異なるsynchronizedメソッド() throws InterruptedException {
        MyClass clazz1 = new MyClass();
        MyClass clazz2 = new MyClass();
        Runnable taskClazz1 = clazz1::syncInstanceMethodA;
        Runnable taskClazz2 = clazz2::syncInstanceMethodB;

        new Thread(taskClazz1).start();
        new Thread(taskClazz2).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行可_synchronizedメソッドとstaticメソッド() throws InterruptedException {
        MyClass clazz = new MyClass();
        Runnable taskSync = clazz::syncInstanceMethodA;
        Runnable taskStatic = MyClass::myStaticMethod;

        new Thread(taskSync).start();
        new Thread(taskStatic).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行可_synchronizedメソッドとstaticSynchronizedメソッド() throws InterruptedException {
        MyClass clazz = new MyClass();
        Runnable taskSync = clazz::syncInstanceMethodA;
        Runnable taskStaticSync = MyClass::syncStaticMethodA;

        new Thread(taskSync).start();
        new Thread(taskStaticSync).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行不可_staticSynchronizedメソッド() throws InterruptedException {
        Runnable taskStaticSyncA = MyClass::syncStaticMethodA;

        new Thread(taskStaticSyncA).start();
        new Thread(taskStaticSyncA).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行不可_異なるstaticSynchronizedメソッド() throws InterruptedException {
        Runnable taskStaticSyncA = MyClass::syncStaticMethodA;
        Runnable taskStaticSyncB = MyClass::syncStaticMethodB;

        new Thread(taskStaticSyncA).start();
        new Thread(taskStaticSyncB).start();
        Thread.sleep(5000);
    }

    @Test
    public void 同時実行不可_異なるインスタンスから異なるstaticSynchronizedメソッド() throws InterruptedException {
        MyClass clazz1 = new MyClass();
        MyClass clazz2 = new MyClass();
        Runnable taskStaticSync1 = () -> {
            clazz1.syncStaticMethodA();
        };
        Runnable taskStaticSync2 = () -> {
            clazz2.syncStaticMethodA();
        };

        new Thread(taskStaticSync1).start();
        new Thread(taskStaticSync2).start();
        Thread.sleep(5000);
    }
}