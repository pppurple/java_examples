package rxjava.base;

import io.reactivex.observers.DisposableSingleObserver;

public class DebugSingleObserver<T> extends DisposableSingleObserver<T> {
    private String label;

    public DebugSingleObserver() {
        super();
    }

    public DebugSingleObserver(String label) {
        super();
        this.label = label;
    }

    @Override
    public void onSuccess(T data) {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + ": " + data);
        } else {
            System.out.println(threadName + ": " + label + ": " + data);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + ": error = " + throwable);
        } else {
            System.out.println(threadName + ": " + label + ": error = " + throwable);
        }
    }
}
