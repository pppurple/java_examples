package multithread.introduction;

public class JoinInterruptMain {
    public static void main(String[] args) throws InterruptedException {

        Thread joinThread  = new Thread(new JoinTask());
        joinThread.start();

        System.out.println(joinThread.getName() + " isInterrupted: " + joinThread.isInterrupted());

        Thread.sleep(1_000);
        joinThread.join(1_000);
        joinThread.interrupt();

        System.out.println(joinThread.getName() + " isInterrupted: " + joinThread.isInterrupted());
    }

    public static class JoinTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " join task start.");
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " Interrupt!");
            }
        }
    }
}
