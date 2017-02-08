package multithread.guardedsuspension;

import java.util.Random;

public class ServerTask implements Runnable {
    private final Random random;
    private final RequestQueue requestQueue;

    public ServerTask(RequestQueue requestQueue, long seed) {
        this.requestQueue = requestQueue;
        this.random = new Random(seed);
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Request request = requestQueue.getRequest();
            System.out.println(Thread.currentThread().getName() + " handles " + request);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
