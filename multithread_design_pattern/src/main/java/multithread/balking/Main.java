package multithread.balking;

public class Main {
    public static void main(String[] args) {
        Data data = new Data("balking_data.txt", "(empty)");
        new Thread(new ChangerTask(data), "ChangerThread").start();
        new Thread(new SaverTask(data), "SaverThread").start();
    }
}
