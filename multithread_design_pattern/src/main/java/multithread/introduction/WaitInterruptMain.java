package multithread.introduction;

public class WaitInterruptMain {
    public static void main(String[] args) throws InterruptedException {

        Thread waitThread = new Thread(new WaitTask());
        waitThread.start();

        System.out.println(waitThread.getName() + " isInterrupted: " + waitThread.isInterrupted());

        Thread.sleep(1_000);
        waitThread.interrupt();

        System.out.println(waitThread.getName() + " isInterrupted: " + waitThread.isInterrupted());
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
}
