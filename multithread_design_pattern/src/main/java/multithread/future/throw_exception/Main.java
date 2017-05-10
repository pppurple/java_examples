package multithread.future.throw_exception;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException {
        System.out.println("main BEGIN");
        Host host = new Host();

        Data data = host.request(-1, 'N');

        System.out.println("data = " + data.getContent());

        System.out.println("main END");
    }
}
