package multithread.future.throw_exception;

import multithread.future.RealData;

import java.util.concurrent.ExecutionException;

public class FutureData implements Data {
    private RealData realdata = null;
    private ExecutionException exception = null;
    private boolean ready = false;

    public synchronized void setRealData(RealData realdata) {
        if (ready) {
            return; // bulk
        }
        this.realdata = realdata;
        this.ready = true;
        notifyAll();
    }

    public synchronized void setException(Throwable throwable) {
        if (ready) {
            return;
        }
        this.exception = new ExecutionException(throwable);
        this.ready = true;
        notifyAll();
    }

    @Override
    public synchronized String getContent() throws ExecutionException {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (exception != null) {
            throw exception;
        }
        return realdata.getContent();
    }
}
