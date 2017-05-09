package multithread.future.retrieve;

public class AsyncContentImpl implements Content {
    private SyncContentImpl syncContent;
    private boolean ready = false;

    public synchronized void setContent(SyncContentImpl syncContent) {
        this.syncContent = syncContent;
        this.ready = true;
        notifyAll();
    }

    @Override
    public synchronized byte[] getBytes() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        return syncContent.getBytes();
    }
}
