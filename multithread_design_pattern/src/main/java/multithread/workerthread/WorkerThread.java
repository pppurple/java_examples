package multithread.workerthread;

public class WorkerThread implements Runnable {
    private final Channel channel;

    public WorkerThread(Channel channel) {
        this.channel = channel;
    }

    public void run() {
        while (true) {
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}
