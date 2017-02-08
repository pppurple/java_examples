package multithread.guardedsuspension;

public class Main {
    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        new Thread(new ClientTask(requestQueue, 123456L), "my client1").start();
        new Thread(new ServerTask(requestQueue, 108932L), "my server1").start();
    }
}
