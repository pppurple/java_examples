package multithread.twophasetermination.gracefulThread;

public class Main {
    public static void main(String[] args) {
        try {
            CountupThread countupThread = new CountupThread();
            countupThread.start();

            Thread.sleep(10_000L);

            System.out.println("main: shutdownRequest");
            countupThread.shutdownRequest();

            System.out.println("main: join");

            countupThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main: END");
    }
}
