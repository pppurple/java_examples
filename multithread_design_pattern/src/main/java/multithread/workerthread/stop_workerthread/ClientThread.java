package multithread.workerthread.stop_workerthread;

import java.util.Random;

public class ClientThread extends Thread {
    private final Channel channel;
    private static final Random random = new Random();
    private volatile boolean terminated = false;

    public ClientThread(Channel channel, String name) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; !terminated; i++) {
                Request request = new Request(Thread.currentThread().getName(), i);
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1_000));
            }
        } catch (InterruptedException e) {
            terminated = true;
        } finally {
            System.out.println(Thread.currentThread().getName() + " is terminated.");
        }
    }

    public void stopThread() {
        terminated = true;
        Thread.interrupted();
    }
}
