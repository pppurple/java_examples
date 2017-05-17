package multithread.activeobject.withconcurrent;

import multithread.activeobject.withconcurrent.activeobject.ActiveObject;

public class DisplayClientThread extends Thread {
    private final ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    public void run() {
        try {
            for (int i = 0; true; i++) {
                String string = Thread.currentThread().getName() + " " + i;
                activeObject.displayString(string);
                Thread.sleep(200L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
