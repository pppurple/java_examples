package multithread.producer_consumer;

import java.util.Random;

public class MakerTask implements Runnable {
    private final Random random;
    private final Table table;
    private static int id = 0;

    public MakerTask(Table table, long seed) {
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1_000));
                String cake = "[ Cake No." + nextId() + " by " + Thread.currentThread().getName() + " ]";
                table.put(cake);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized int nextId() {
        return id++;
    }
}
