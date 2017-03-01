package multithread.workerthread.stop_workerthread;

public class WorkerThread extends Thread {
    private final Channel channel;
    private volatile boolean terminated = false;

    public WorkerThread(Channel channel, String name) {
        super(name);
        this.channel = channel;
    }

    public void run() {
        try {
            while (!terminated) {
                Request request = null;
                request = channel.takeRequest();
                request.execute();
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
