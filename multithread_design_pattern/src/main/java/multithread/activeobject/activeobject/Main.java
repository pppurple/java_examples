package multithread.activeobject.activeobject;

import multithread.activeobject.DisplayClientThread;
import multithread.activeobject.MakerClientThread;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice", activeObject).start();
        new MakerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }
}
