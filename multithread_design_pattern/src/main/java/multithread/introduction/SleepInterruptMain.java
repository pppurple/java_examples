package multithread.introduction;

public class SleepInterruptMain {
    public static void main(String[] args) throws InterruptedException {

        Thread sleepThread = new Thread(new SleepTask());
        Thread interruptThread = new Thread(new InterruptTask(sleepThread));

        sleepThread.start();
        Thread.sleep(1_000);
        System.out.println(sleepThread.getName() + " isInterrupted: " + sleepThread.isInterrupted());
        Thread.sleep(1_000);
        interruptThread.start();
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

    public static class InterruptTask implements Runnable {
        private final Thread target;

        public InterruptTask(Thread target) {
            this.target = target;
        }

        @Override
        public void run() {
            target.interrupt();
        }
    }
}
