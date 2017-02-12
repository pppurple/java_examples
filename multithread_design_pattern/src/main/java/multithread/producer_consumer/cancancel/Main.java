package multithread.producer_consumer.cancancel;

public class Main {
    public static void main(String[] args) {
        Thread executor = new Thread() {
            @Override
            public void run() {
                System.out.println("Host.execute BEGIN");
                try {
                    Host.execute(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Host.execute END");
            }
        };

        executor.start();

        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // do cancel
        System.out.println("****** interrupt ******");
        executor.interrupt();
    }
}
