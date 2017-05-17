package multithread.activeobject.withconcurrent;

import multithread.activeobject.withconcurrent.activeobject.ActiveObject;
import multithread.activeobject.withconcurrent.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        try {
            new MakerClientThread("Alice", activeObject).start();
            new MakerClientThread("Bobby", activeObject).start();
            new DisplayClientThread("Alice", activeObject).start();
        } finally {
            System.out.println("*** shutdown ***");
            activeObject.shutdown();
        }
    }
}
