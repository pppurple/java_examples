package webserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TcpClient {
    public static void main(String[] args) {
        Path clientSend = Paths.get("src/main/java/webserver/client_send.txt").toAbsolutePath();
        Path clientRecv = Paths.get("src/main/java/webserver/client_recv.txt").toAbsolutePath();

        try (Socket socket = new Socket("localhost", 8080);
             FileInputStream fis = new FileInputStream(clientSend.toFile());
             FileOutputStream fos = new FileOutputStream(clientRecv.toFile())) {

            int ch;
            OutputStream output = socket.getOutputStream();
            while ((ch = fis.read()) != -1) {
                output.write(ch);
            }

            output.write(0);

            InputStream input = socket.getInputStream();
            while ((ch = input.read()) != -1) {
                fos.write(ch);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
