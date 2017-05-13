package multithread.twophasetermination.shutdownhook;

public class Main {
    public static void main(String[] args) {
        System.out.println("main:BEGIN");

        Thread.setDefaultUncaughtExceptionHandler(
                (t, e) -> {
                    System.out.println("******");
                    System.out.println("UncaughtExceptionHandler:BEGIN");
                    System.out.println("currentThread = " + Thread.currentThread());
                    System.out.println("thread = " + t);
                    System.out.println("exception = " + e);
                    System.out.println("UncaughtExceptionHandler:END");
                }
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    System.out.println("******");
                    System.out.println("shutdown hook:BEGIN");
                    System.out.println("currentThread = " + Thread.currentThread());
                    System.out.println("shutdown hook:END");
                })
        );

        new Thread("MyThread") {
            public void run() {
                System.out.println("MyThread:BEGIN");
                System.out.println("MyThread:SLEEP....");

                try {
                    Thread.sleep(3_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("MyThread:DIVIDE");

                int x = 1 / 0;

                System.out.println("MyThread:END");
            }
        }.start();

        System.out.println("main:END");
    }
}
