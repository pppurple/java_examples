package multithread.future.throw_exception;

import multithread.future.RealData;

public class Host {
    public Data request(final int count, final char c) {
        System.out.println("    request(" + count + ", " + ") BEGIN");

        final FutureData future = new FutureData();

        new Thread() {
            public void run() {
                try {
                    RealData realData = new RealData(count, c);
                    future.setRealData(realData);
                } catch (Exception e) {
                    future.setException(e);
                }
            }
        }.start();

        System.out.println("    request(" + count + ", " + ") END");

        return future;
    }
}
