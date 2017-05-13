package multithread.twophasetermination.hanoi;

public class HanoiThread extends Thread {
    private volatile boolean shutdownRequest = false;
    private volatile long requestedTimeMillis = 0;

    public void shutdownRequest() {
        requestedTimeMillis = System.currentTimeMillis();
        shutdownRequest = true;
        interrupt();
    }

    public boolean isShutdownRequest() {
        return shutdownRequest;
    }

    public void run() {
        try {
            for (int level = 0; !isShutdownRequest(); level++) {
                System.out.println("==== Level " + level + "====");
                doWork(level, 'A', 'B', 'C');
                System.out.println("");
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    private void doWork(int level, char posA, char posB, char posC) throws InterruptedException {
        if (level > 0) {
            doWork(level - 1, posA, posC, posB);
            System.out.print(posA + "->" + posB + " ");
            doWork(level - 1, posC, posB, posA);
        }
    }

    private void doShutdown() {
        long time = System.currentTimeMillis() - requestedTimeMillis;
        System.out.println("doShutdown: Latency = " + time + " msec.");
    }
}
