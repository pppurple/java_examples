package multithread.threadspecificstorage.terminate;

import multithread.threadspecificstorage.TSLog;

public class Log {
    private static final ThreadLocal<TSLog> tsLogCollection = new ThreadLocal<>();

    public static void println(String s) {
        getTSLog().println(s);
    }

    public static void close() {
        getTSLog().close();
    }

    private static TSLog getTSLog() {
        TSLog tsLog = tsLogCollection.get();

        if (tsLog == null) {
            tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLogCollection.set(tsLog);
            startWatcher(tsLog);
        }

        return tsLog;
    }

    private static void startWatcher(final TSLog tsLog) {
        final Thread target = Thread.currentThread();

        final Thread watcher = new Thread(() -> {
            System.out.println("startWatcher for " + target.getName() + " BEGIN");
            try {
                target.join();
            } catch (InterruptedException ignored) {
            }
            System.out.println("startWatcher for " + target.getName() + " END");
        });

        watcher.start();
    }
}
