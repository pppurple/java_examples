package javase8;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by pppurple on 2016/08/13.
 */
public class EnvelopeTest {
    @Test
    public void printTest() throws Exception {
        Envelope<String> container = Envelope.create("Hello, World!");

        // 標準出力へ出力
        container.print(contents -> System.out.println(contents.toString()));

        // ファイルへ出力
        container.print(contents -> {
            try {
                Path path = Paths.get("contents.txt");
                Files.write(path, contents.toString().getBytes());
            } catch (IOException e) {

            }
        });
    }

}