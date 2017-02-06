package multithread.introduction;

public class WaitAndNotifyAllMain {
    public static void main(String[] args) throws InterruptedException {
        WaitAndNotifyAll waitAndNotifyAll = new WaitAndNotifyAll();

        Runnable taskWait = waitAndNotifyAll::threadWait;
        new Thread(taskWait).start();
        new Thread(taskWait).start();
        new Thread(taskWait).start();
        new Thread(taskWait).start();

        // 3,000msec待機
        Thread.sleep(3000);

        Runnable taskNotifyAll = waitAndNotifyAll::threadNotifyAll;
        new Thread(taskNotifyAll).start();
    }
}
