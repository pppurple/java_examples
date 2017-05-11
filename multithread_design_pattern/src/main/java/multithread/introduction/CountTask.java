package multithread.introduction;

public class CountTask implements Runnable {
    private int count;

    public CountTask(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for(int i = 0; i < count; i++) {
            try {
                Thread.sleep(400L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " : " + i);
        }
    }
}
