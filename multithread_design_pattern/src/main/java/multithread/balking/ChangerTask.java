package multithread.balking;

import java.io.IOException;
import java.util.Random;

public class ChangerTask implements Runnable {
    private final Data data;
    private final Random random = new Random();

    public ChangerTask(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                data.change("No." + i);
                Thread.sleep(random.nextInt(1000));
                data.save();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
