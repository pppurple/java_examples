package multithread.workerthread;

public class Main {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorkers();
        new Thread(new ClientThread(channel), "Alice").start();
        new Thread(new ClientThread(channel), "Bobby").start();
        new Thread(new ClientThread(channel), "Cindy").start();
    }
}
