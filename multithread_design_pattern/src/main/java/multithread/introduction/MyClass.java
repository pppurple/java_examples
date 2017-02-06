package multithread.introduction;

public class MyClass {
    public void myInstanceMethod() {
        System.out.println(Thread.currentThread().getName() + ": instanceMethod start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": instanceMethod end");
    }

    public synchronized void syncInstanceMethodA() {
        System.out.println(Thread.currentThread().getName() + ": syncInstanceMethodA start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": syncInstanceMethodA end");
    }

    public synchronized void syncInstanceMethodB() {
        System.out.println(Thread.currentThread().getName() + ": syncInstanceMethodB start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": syncInstanceMethodB end");
    }

    public static void myStaticMethod() {
        System.out.println(Thread.currentThread().getName() + ": staticMethod start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": staticMethod end");
    }

    public static synchronized void syncStaticMethodA() {
        System.out.println(Thread.currentThread().getName() + ": syncStaticMethodA start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": syncStaticMethodA end");
    }

    public static synchronized void syncStaticMethodB() {
        System.out.println(Thread.currentThread().getName() + ": syncStaticMethodB start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": syncStaticMethodB end");
    }
}
