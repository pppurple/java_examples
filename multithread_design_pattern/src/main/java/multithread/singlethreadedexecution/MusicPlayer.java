package multithread.singlethreadedexecution;

public class MusicPlayer {
    private int playCount = 0;
    private String userName = "";

    public synchronized void play(String userName) {
        playCount++;
        this.userName = userName;
        System.out.println(this);
    }

    @Override
    public synchronized String toString() {
        return "play count:" + playCount + ", user name:" + userName;
    }
}
