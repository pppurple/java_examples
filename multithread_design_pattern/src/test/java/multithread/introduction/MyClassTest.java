package multithread.introduction;

import org.junit.Test;

public class MyClassTest {
    @Test
    public void 同時実行可_インスタンスメソッド() {
        MyClass clazz = new MyClass();
        Runnable task = clazz::myMethod;

        new Thread(task).start();
        new Thread(task).start();
    }

    @Test
    public void 同時実行可_インスタンスメソッドとsynchronizedメソッド() {
        MyClass clazz = new MyClass();
        Runnable taskNonSync = clazz::myMethod;
        Runnable taskSync = clazz::syncMethodA;

        new Thread(taskNonSync).start();
        new Thread(taskSync).start();
    }

    @Test
    public void 同時実行不可_synchronizedメソッド() {
        MyClass clazz = new MyClass();
        Runnable taskSync = clazz::syncMethodA;

        new Thread(taskSync).start();
        new Thread(taskSync).start();
    }

    @Test
    public void 同時実行不可_異なるsynchronizedメソッド() {
        MyClass clazz = new MyClass();
        Runnable taskSyncA = clazz::syncMethodA;
        Runnable taskSyncB = clazz::syncMethodB;

        new Thread(taskSyncA).start();
        new Thread(taskSyncB).start();
    }

    @Test
    public void 同時実行可_異なるインスタンスのsynchronizedメソッド() {
        MyClass clazz1 = new MyClass();
        MyClass clazz2 = new MyClass();
        Runnable taskClazz1 = clazz1::syncMethodA;
        Runnable taskClazz2 = clazz2::syncMethodA;

        new Thread(taskClazz1).start();
        new Thread(taskClazz2).start();
    }

    @Test
    public void 同時実行可_異なるインスタンスの異なるsynchronizedメソッド() {
        MyClass clazz1 = new MyClass();
        MyClass clazz2 = new MyClass();
        Runnable taskClazz1 = clazz1::syncMethodA;
        Runnable taskClazz2 = clazz2::syncMethodB;

        new Thread(taskClazz1).start();
        new Thread(taskClazz2).start();
    }

    @Test
    public void 同時実行可_synchronizedメソッドとstaticメソッド() {
        MyClass clazz = new MyClass();
        Runnable taskSync = clazz::syncMethodA;
        Runnable taskStatic = MyClass::myStaticMethod;

        new Thread(taskSync).start();
        new Thread(taskStatic).start();
    }

    @Test
    public void 同時実行可_synchronizedメソッドとstaticSynchronizedメソッド() {
        MyClass clazz = new MyClass();
        Runnable taskSync = clazz::syncMethodA;
        Runnable taskStaticSync = MyClass::syncStaticMethodA;

        new Thread(taskSync).start();
        new Thread(taskStaticSync).start();
    }

    @Test
    public void 同時実行不可_staticSynchronizedメソッド() {
        Runnable taskStaticSyncA = MyClass::syncStaticMethodA;

        new Thread(taskStaticSyncA).start();
        new Thread(taskStaticSyncA).start();
    }

    @Test
    public void 同時実行不可_異なるstaticSynchronizedメソッド() {
        Runnable taskStaticSyncA = MyClass::syncStaticMethodA;
        Runnable taskStaticSyncB = MyClass::syncStaticMethodB;

        new Thread(taskStaticSyncA).start();
        new Thread(taskStaticSyncB).start();
    }

    @Test
    public void 同時実行不可_異なるインスタンスから異なるstaticSynchronizedメソッド() {
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
    }
}