package multithread.activeobject.activeobject;

public class RealResult<T> extends Result<T> {
    private final T resultValue;

    public RealResult(T resultValue) {
        this.resultValue = resultValue;
    }
    @Override
    public T getResultValue() {
        return resultValue;
    }
}
