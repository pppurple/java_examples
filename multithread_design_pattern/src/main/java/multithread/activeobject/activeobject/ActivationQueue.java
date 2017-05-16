package multithread.activeobject.activeobject;

public class ActivationQueue {
    private static final int MAX_METHOD_REQUEST = 100;
    private final MethodRequest[] requestQueue;
    private int tail;
    private int head;
    private int count;

    public ActivationQueue() {
        this.requestQueue = new MethodRequest[MAX_METHOD_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    public synchronized void putRequest(MethodRequest request) {
        while (count >= requestQueue.length) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        requestQueue[tail] = request;
        tail = (tail + 1) % requestQueue.length;
        count++;
        notifyAll();
    }

    public synchronized MethodRequest takeRequest() {
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        MethodRequest request = requestQueue[head];
        head = (head + 1) % requestQueue.length;
        count--;
        notifyAll();
        return request;
    }
}
