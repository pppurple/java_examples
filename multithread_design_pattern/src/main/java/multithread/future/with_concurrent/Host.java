package multithread.future.with_concurrent;

import multithread.future.RealData;

public class Host {
    public FutureData request(final int count, final char c) {
        System.out.println("    request(" + count + ", " + c + ") BEGIN");

        FutureData future = new FutureData(
                () -> new RealData(count, c)
        ) ;

        new Thread(future).start();

        System.out.println("    request(" + count + ", " + c + ") END");

        return future;
    }
}
