package multithread.introduction;

public class WaitAndNotifyMain {
    public static void main(String[] args) {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

        Runnable taskWait = waitAndNotify::waitThread;
        new Thread(taskWait).start();

        Runnable taskNotify = waitAndNotify::notifyThread;
        new Thread(taskNotify).start();
    }
}
