package multithread.introduction;

public class MyMethodInterruptMain {
    public static void main(String[] args) throws InterruptedException {

        Thread myThread = new Thread(new MyTask());
        myThread.start();

        System.out.println(myThread.getName() + " isInterrupted: " + myThread.isInterrupted());

        Thread.sleep(1_000);
        myThread.interrupt();

        System.out.println(myThread.getName() + " isInterrupted: " + myThread.isInterrupted());
    }

    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " MyTask start.");
            myMethod();
        }
    }

    public static void myMethod() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " Interrupt!");
                return;
            }
        }
    }
}
