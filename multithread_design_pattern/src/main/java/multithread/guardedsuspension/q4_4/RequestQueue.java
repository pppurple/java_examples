package multithread.guardedsuspension.q4_4;

import multithread.guardedsuspension.Request;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {
    private static final long TIMEOUT = 30_000L;
    private final Queue<Request> queue = new LinkedList<>();

    public synchronized Request getRequest() {
        long start = System.currentTimeMillis();
        while (queue.peek() == null) {
            long now = System.currentTimeMillis();
            long rest = TIMEOUT - (now - start);
            if (rest <= 0) {
                throw new LivenessException("thrown by " + Thread.currentThread().getName());
            }
            try {
                wait(rest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.remove();
    }

    public synchronized void putRequest(Request request) {
        queue.offer(request);
        notifyAll();
    }
}
