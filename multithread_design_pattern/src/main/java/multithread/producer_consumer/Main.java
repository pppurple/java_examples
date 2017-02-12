package multithread.producer_consumer;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(3);
        new Thread(new MakerTask(table, 123456L), "Maker1").start();
        new Thread(new MakerTask(table, 234567L), "Maker2").start();
        new Thread(new MakerTask(table, 345678L), "Maker3").start();
        new Thread(new EaterTask(table, 321321L), "Eater1").start();
        new Thread(new EaterTask(table, 432432L), "Eater2").start();
        new Thread(new EaterTask(table, 543543L), "Eater3").start();
    }
}
