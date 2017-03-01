package multithread.workerthread.stop_workerthread;

public class Main {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorkers();

        ClientThread alice = new ClientThread(channel, "Alice");
        ClientThread bobby = new ClientThread(channel, "Bobby");
        ClientThread cindy = new ClientThread(channel, "Cindy");
        alice.start();
        bobby.start();
        cindy.start();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
        }
        alice.stopThread();
        bobby.stopThread();
        cindy.stopThread();
        channel.stopWorkers();
    }
}
