package multithread.twophasetermination.gracefulThread;

public class CountupThread extends GracefulThread {
    private long counter = 0;

    @Override
    public void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        Thread.sleep(500L);
    }

    @Override
    protected void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }
}
