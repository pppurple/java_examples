package multithread.producer_consumer.cancancel;

public class Host {
    public static void execute(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            doHeavyJob();
        }
    }

    private static void doHeavyJob() {
        System.out.println("doHeavyJob BEGIN");
        long start = System.currentTimeMillis();
        while (start + 10_000 > System.currentTimeMillis()) {
            // busy loop
        }
        System.out.println("doHeavyJob END");
    }
}
