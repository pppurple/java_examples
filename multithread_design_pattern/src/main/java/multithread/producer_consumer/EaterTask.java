package multithread.producer_consumer;

import java.util.Random;

public class EaterTask implements Runnable {
    private final Random random;
    private final Table table;

    public EaterTask(Table table, long seed) {
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String cake = table.take();
                Thread.sleep(random.nextInt(1_000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
