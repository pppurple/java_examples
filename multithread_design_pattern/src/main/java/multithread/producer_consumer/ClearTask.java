package multithread.producer_consumer;

public class ClearTask implements Runnable {
    private final Table table;

    public ClearTask(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1_000);
                System.out.println("=======" + Thread.currentThread().getName() + " clears ======");
                table.clear();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
