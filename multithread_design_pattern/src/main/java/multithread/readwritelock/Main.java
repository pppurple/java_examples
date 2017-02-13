package multithread.readwritelock;

public class Main {
    public static void main(String[] args) {
        Data data = new Data(10);
        new Thread(new ReaderTask(data)).start();
        new Thread(new ReaderTask(data)).start();
        new Thread(new ReaderTask(data)).start();
        new Thread(new ReaderTask(data)).start();
        new Thread(new ReaderTask(data)).start();
        new Thread(new ReaderTask(data)).start();
        new Thread(new WriterTask(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ")).start();
        new Thread(new WriterTask(data, "abcdefghijklmnopqrstuvwxyz")).start();
    }
}
