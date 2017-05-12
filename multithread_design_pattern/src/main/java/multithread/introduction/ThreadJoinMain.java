package multithread.introduction;

public class ThreadJoinMain {
    public static void main(String[] args) throws InterruptedException {
        Thread myTask = new Thread(new ThreadIsAliveMain.MyTask());
        myTask.start();

        System.out.println("isAlive: " + myTask.isAlive());
        System.out.println("state: " + myTask.getState().name());

        myTask.join();

        System.out.println("isAlive: " + myTask.isAlive());
        System.out.println("state: " + myTask.getState().name());
    }
}
