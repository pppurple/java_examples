package multithread.introduction;

public class SleepInterruptMain {
    public static void main(String[] args) throws InterruptedException {

        Thread sleepThread = new Thread(new SleepTask());
        sleepThread.start();

        System.out.println(sleepThread.getName() + " isInterrupted: " + sleepThread.isInterrupted());

        Thread.sleep(1_000);
        sleepThread.interrupt();

        System.out.println(sleepThread.getName() + " isInterrupted: " + sleepThread.isInterrupted());
    }

    public static class SleepTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " sleep start.");
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " Interrupt!");
            }
        }
    }
}
