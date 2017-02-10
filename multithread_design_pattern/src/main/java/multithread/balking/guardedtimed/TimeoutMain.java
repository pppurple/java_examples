package multithread.balking.guardedtimed;

import java.util.concurrent.TimeoutException;

public class TimeoutMain {
    // timeoutするパターン
    public static void main(String[] args) {
        Host host = new Host(5_000);
        System.out.println("execute BEGIN");
        try {
            host.execute();
        } catch (TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
