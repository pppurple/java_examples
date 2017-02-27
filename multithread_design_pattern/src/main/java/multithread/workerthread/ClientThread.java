package multithread.workerthread;

import java.util.Random;

public class ClientThread implements Runnable {
    private final Channel channel;
    private static final Random random = new Random();

    public ClientThread(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Request request = new Request(Thread.currentThread().getName(), i);
                channel.putRequest(request);
                Thread.sleep(1_000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
