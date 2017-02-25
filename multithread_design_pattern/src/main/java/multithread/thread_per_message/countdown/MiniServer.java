package multithread.thread_per_message.countdown;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MiniServer {
    private final int port;

    public MiniServer(int port) {
        this.port = port;
    }

    public void execute() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on " + serverSocket);
            while (true) {
                System.out.println("Accepting...");
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to " + clientSocket);
                new Thread(() -> {
                    try {
                        Service.service(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
