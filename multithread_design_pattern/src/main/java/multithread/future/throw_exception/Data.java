package multithread.future.throw_exception;

import java.util.concurrent.ExecutionException;

public interface Data {
    public abstract String getContent() throws ExecutionException;
}
