package multithread.activeobject.withconcurrent.activeobject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActiveObjectImpl implements ActiveObject {
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    public Future<String> makeString(int count, char fillchar) {
        Callable<String> makeStringRequest = () -> {
            char[] buffer = new char[count];
            for (int i = 0; i < count; i++) {
                buffer[i] = fillchar;
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ignored) {
                }
            }
            return new String(buffer);
        };
        return service.submit(makeStringRequest);
    }

    @Override
    public void displayString(String string) {
        new Runnable() {
            @Override
            public void run() {
                System.out.println("displayString: " + string);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
            }
        };
    }

    @Override
    public void shutdown() {
        service.shutdown();
    }
}
