package rxjava.base;

import io.reactivex.subscribers.DisposableSubscriber;

public class DebugSubscriber<T> extends DisposableSubscriber<T> {
    private String label;

    public DebugSubscriber() {
        super();
    }

    public DebugSubscriber(String label) {
        super();
        this.label = label;
    }

    @Override
    public void onNext(T data) {
        if (label == null) {
            System.out.println(Thread.currentThread().getName() + ": " + data);
        } else {
            System.out.println(Thread.currentThread().getName() + ": " + label + ": " + data);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (label == null) {
            System.out.println(Thread.currentThread().getName() + ": error=" + throwable);
        } else {
            System.out.println(Thread.currentThread().getName() + ": " + label + ": error=" + throwable);
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
