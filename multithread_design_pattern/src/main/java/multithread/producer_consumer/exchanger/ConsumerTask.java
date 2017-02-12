package multithread.producer_consumer.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ConsumerTask implements Runnable {
    private final Exchanger<char[]> exchanger;
    private char[] buffer = null;
    private final Random random;

    public ConsumerTask(Exchanger<char[]> exchanger, char[] buffer, long seed) {
        this.exchanger = exchanger;
        this.buffer = buffer;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ": BEFORE exchange");
                buffer = exchanger.exchange(buffer);
                System.out.println(Thread.currentThread().getName() + ": AFTER exchange");

                for (int i = 0; i < buffer.length; i++) {
                    System.out.println(Thread.currentThread().getName() + ": -> " + buffer[i]);
                    Thread.sleep(random.nextInt(1_000));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
