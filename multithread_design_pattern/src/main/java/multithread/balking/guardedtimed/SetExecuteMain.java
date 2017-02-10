package multithread.balking.guardedtimed;

import java.util.concurrent.TimeoutException;

public class SetExecuteMain {
    // timeoutする前にsetExecute(true)するパターン
    public static void main(String[] args) {
        Host host = new Host(5_000);
        System.out.println("execute BEGIN");
        try {
            host.setExecutable(true);
            host.execute();
        } catch (TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
