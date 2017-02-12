package multithread.guardedsuspension.q4_4;

import multithread.guardedsuspension.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class RequestQueueWithLinkedBlockingQueue {
    private final BlockingQueue<Request> queue = new LinkedBlockingDeque<>();

    public synchronized Request getRequest() {
        Request req = null;
        try {
            req = queue.poll(30L, TimeUnit.SECONDS);
            if (req == null) {
                throw new LivenessException("thrown by " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return req;
    }

    public synchronized void putRequest(Request request) {
        try {
            boolean offered = queue.offer(request, 30L, TimeUnit.SECONDS);
            if (!offered) {
                throw new LivenessException("thrown by " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
