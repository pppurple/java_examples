package multithread.future.retrieve;

public class Retriever {
    public static Content retrieve(final String urlstr) {
        // sync
        // return new SyncContentImpl(urlstr);

        final AsyncContentImpl future = new AsyncContentImpl();

        new Thread(() -> future.setContent(new SyncContentImpl(urlstr))).start();

        return future;
    }
}
