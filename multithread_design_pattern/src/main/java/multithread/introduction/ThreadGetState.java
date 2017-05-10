package multithread.introduction;

public class ThreadGetState {
    public static void main(String[] args) throws InterruptedException {
        Thread myTask = new Thread(new ThreadIsAliveMain.MyTask());
        myTask.start();

        while (true) {
            Thread.State state = myTask.getState();
            System.out.println("state: " + state.name());
            Thread.sleep(1_000L);
        }
    }
}
