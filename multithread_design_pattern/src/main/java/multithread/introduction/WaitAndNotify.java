package multithread.introduction;

public class WaitAndNotify {
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
        notify();
        System.out.println("notified!");
    }
}
