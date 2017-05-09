package multithread.future.retrieve_with_future_task;

import multithread.future.retrieve.Content;
import multithread.future.retrieve.SyncContentImpl;

import java.util.concurrent.Callable;

public class Retriever {
    public static Content retrieve(final String urlstr) {
        AsyncContentImpl future = new AsyncContentImpl(
                new Callable<SyncContentImpl>() {
                    @Override
                    public SyncContentImpl call() throws Exception {
                        return new SyncContentImpl(urlstr);
                    }
                }
        );

        new Thread(future).start();

        return future;
    }
}
