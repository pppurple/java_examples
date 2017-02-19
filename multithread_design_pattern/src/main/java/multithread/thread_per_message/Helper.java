package multithread.thread_per_message;

import java.util.stream.IntStream;

public class Helper {
    public void handle(int count, char c) {
        System.out.println("    handle(" + count + ", " + c + ") BEGIN");
        IntStream.range(0, count)
                .forEach(i -> {
                    slowly();
                    System.out.print(c);
                });
        System.out.println("");
        System.out.println("    handle(" + count + ", " + c + ") END");
    }

    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
