package multithread.introduction;

public class WaitAndNotifyAll {
    synchronized void threadWait() {
        System.out.println(Thread.currentThread().getName() + ": wait!");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": unlocked!");
    }

    synchronized void threadNotifyAll() {
        notifyAll();
        System.out.println("notified ALL!");
    }
}
