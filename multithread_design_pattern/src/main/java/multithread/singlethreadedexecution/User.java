package multithread.singlethreadedexecution;

public class User implements Runnable {
    private String name;
    private MusicPlayer player;

    public User(String name, MusicPlayer player) {
        this.name = name;
        this.player = player;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            player.play(name);
        }
    }

    public String getName() {
        return name;
    }
}
