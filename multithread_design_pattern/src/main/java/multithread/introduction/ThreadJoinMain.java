package multithread.introduction;

public class ThreadJoinMain {
    public static void main(String[] args) throws InterruptedException {
        Thread myTask = new Thread(new ThreadIsAliveMain.MyTask());
        myTask.start();

        System.out.println("isAlive: " + myTask.isAlive());

        myTask.join();

        System.out.println("isAlive: " + myTask.isAlive());
    }
}
