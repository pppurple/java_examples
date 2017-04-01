package rxjava.base;

import io.reactivex.observers.DisposableMaybeObserver;

public class DebugMaybeObserver<T> extends DisposableMaybeObserver<T> {
    private String label;

    public DebugMaybeObserver() {
        super();
    }

    public DebugMaybeObserver(String label) {
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

    @Override
    public void onComplete() {
        if (label == null) {
            System.out.println(Thread.currentThread().getName() + ": complete");
        } else {
            System.out.println(Thread.currentThread().getName() + ": " + label + ": complete");
        }
    }
}
