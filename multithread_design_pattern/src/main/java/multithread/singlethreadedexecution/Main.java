package multithread.singlethreadedexecution;

public class Main {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();
        Thread taskA = new Thread(new User("Anny", player));
        Thread taskB = new Thread(new User("Bobby", player));
        Thread taskC = new Thread(new User("Cindy", player));

        taskA.start();
        taskB.start();
        taskC.start();
    }
}
