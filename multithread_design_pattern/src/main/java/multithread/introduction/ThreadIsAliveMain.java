package multithread.introduction;

public class ThreadIsAliveMain {
    public static void main(String[] args) throws InterruptedException {
        Thread myTask = new Thread(new MyTask());
        myTask.start();

        while (true) {
            System.out.println("isAlive: " + myTask.isAlive());
            Thread.sleep(1_000L);
        }
    }

    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " START");

            int count = 0;
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(count);
                    count++;
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " END");
        }
    }
}
