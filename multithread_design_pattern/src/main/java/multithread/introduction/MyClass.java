package multithread.introduction;

public class MyClass {
    public void myMethod() {
        System.out.println("myMethod");
    }

    public synchronized void syncMethodA() {
        System.out.println("syncMethodA");
    }

    public synchronized void syncMethodB() {
        System.out.println("syncMethodB");
    }

    public static void myStaticMethod() {
        System.out.println("myMethod");
    }

    public static synchronized void syncStaticMethodA() {
        System.out.println("syncStaticMethodA");
    }

    public static synchronized void syncStaticMethodB() {
        System.out.println("syncStaticMethodB");
    }
}
