package multithread.future.retrieve_with_future_task;

import multithread.future.retrieve.Content;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Content content1 = Retriever.retrieve("https://www.yahoo.com/");
        Content content2 = Retriever.retrieve("http://www.google.com/");
        Content content3 = Retriever.retrieve("http://www.hyuki.com/");

        saveToFile("yahoo.html", content1);
        saveToFile("google.html", content2);
        saveToFile("hyuki.html", content3);

        long end = System.currentTimeMillis();
        System.out.println("Elapsed time = " + (end - start) + "msec.");
    }

    private static void saveToFile(String filename, Content content) {
        byte[] bytes = content.getBytes();
        try(FileOutputStream out = new FileOutputStream(filename)) {
            System.out.println(Thread.currentThread().getName() + ": Saving to " + filename);
            for (byte b : bytes) {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
