package webserver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TcpServer {
    public static void main(String[] args) {
        Path serverRecv = Paths.get("src/main/java/webserver/server_recv.txt").toAbsolutePath();
        Path serverSend = Paths.get("src/main/java/webserver/server_send.txt").toAbsolutePath();

        try (ServerSocket server = new ServerSocket(8080);
             FileOutputStream fos = new FileOutputStream(serverRecv.toFile());
             FileInputStream fis = new FileInputStream(serverSend.toFile())) {
            System.out.println("Waiting to connecting from client");
            Socket socket = server.accept();
            System.out.println("connect");

            int ch;
            InputStream input = socket.getInputStream();
            while ((ch = input.read()) != 0) {
                fos.write(ch);
            }

            OutputStream output = socket.getOutputStream();
            while ((ch = fis.read()) != -1) {
                output.write(ch);
            }
            socket.close();
            System.out.println("end");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
