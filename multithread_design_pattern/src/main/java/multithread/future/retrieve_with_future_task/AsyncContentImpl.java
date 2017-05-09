package multithread.future.retrieve_with_future_task;

import multithread.future.retrieve.Content;
import multithread.future.retrieve.SyncContentImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class AsyncContentImpl extends FutureTask<SyncContentImpl> implements Content {
    public AsyncContentImpl(Callable<SyncContentImpl> callable) {
        super(callable);
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = null;
        try {
            bytes = get().getBytes();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
