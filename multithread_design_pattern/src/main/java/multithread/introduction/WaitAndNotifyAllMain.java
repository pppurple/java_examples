package multithread.introduction;

public class WaitAndNotifyAllMain {
    public static void main(String[] args) {
        WaitAndNotifyAll waitAndNotifyAll = new WaitAndNotifyAll();

        Runnable taskWait = waitAndNotifyAll::waitThread;
        new Thread(taskWait).start();
        new Thread(taskWait).start();
        new Thread(taskWait).start();
        new Thread(taskWait).start();

        Runnable taskNotifyAll = waitAndNotifyAll::notifyThread;
        new Thread(taskNotifyAll).start();
    }
}
