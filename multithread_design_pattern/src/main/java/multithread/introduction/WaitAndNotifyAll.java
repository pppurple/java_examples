package multithread.introduction;

public class WaitAndNotifyAll {
    synchronized void waitThread() {
        System.out.println("wait!");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("unlocked!");
    }

    synchronized void notifyThread() {
        notifyAll();
        System.out.println("notified ALL!");
    }
}
