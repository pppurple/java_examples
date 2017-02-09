package multithread.balking;

import java.io.IOException;

public class SaverTask implements Runnable {
    private final Data data;

    public SaverTask(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.save();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
