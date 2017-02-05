package multithread.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Worker {
    private final Semaphore semaphore;
    private int num;
    private Random random = new Random();

    public Worker(int num) {
        this.semaphore = new Semaphore(num);
        this.num = num;
    }

    public void process() throws InterruptedException {
        // セマフォからパーミット取得
        semaphore.acquire();
        try {
            // dummy処理
            System.out.println(Thread.currentThread().getName() + ", start process. permits:" + semaphore.availablePermits() + "/" + num);
            Thread.sleep(random.nextInt(3000));
//            for (int i = 0; i < 5; i++) {
//                process();
//            }
        } finally {
            // パーミットを開放してセマフォに戻す
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + ",   end process. permits:" + semaphore.availablePermits() + "/" + num);
        }
    }
}
