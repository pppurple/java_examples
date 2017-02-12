package multithread.introduction;

public class WaitInterruptMain {
    public static void main(String[] args) throws InterruptedException {

        Thread waitThread = new Thread(new WaitTask());
        Thread interruptThread = new Thread(new InterruptTask(waitThread));

        waitThread.start();
        Thread.sleep(1_000);
        System.out.println(waitThread.getName() + " isInterrupted: " + waitThread.isInterrupted());
        Thread.sleep(1_000);
        interruptThread.start();
    }

    public static class WaitTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait.");
                doWait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " Interrupt!");
            }
        }

        private synchronized void doWait() throws InterruptedException {
            wait();
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
