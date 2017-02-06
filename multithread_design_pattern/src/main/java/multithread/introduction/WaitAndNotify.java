package multithread.introduction;

public class WaitAndNotify {
    synchronized void threadWait() {
        System.out.println("wait!");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("unlocked!");
    }

    synchronized void threadNotify() {
        notify();
        System.out.println("notified!");
    }
}
