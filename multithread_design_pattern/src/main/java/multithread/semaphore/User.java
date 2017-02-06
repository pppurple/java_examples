package multithread.semaphore;

import java.util.Random;

public class User implements Runnable {
    private final Worker worker;
    private Random random = new Random();

    public User(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(random.nextInt(3000));
                worker.process();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
