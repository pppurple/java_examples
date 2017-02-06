package multithread.introduction;

public class WaitAndNotifyMain {
    public static void main(String[] args) throws InterruptedException {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

        Runnable taskWait = waitAndNotify::threadWait;
        new Thread(taskWait).start();

        // 3,000msec待機
        Thread.sleep(3000);

        Runnable taskNotify = waitAndNotify::threadNotify;
        new Thread(taskNotify).start();
    }
}
