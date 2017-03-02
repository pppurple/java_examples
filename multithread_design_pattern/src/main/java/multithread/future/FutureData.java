package multithread.future;

public class FutureData implements Data {
    private RealData realdata = null;
    private boolean ready = false;

    public synchronized void setRealData(RealData realdata) {
        if (ready) {
            return; // bulk
        }
        this.realdata = realdata;
        this.ready = true;
        notifyAll();
    }

    @Override
    public synchronized String getContent() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realdata.getContent();
    }
}
