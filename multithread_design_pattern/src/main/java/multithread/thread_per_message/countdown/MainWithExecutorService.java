package multithread.thread_per_message.countdown;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWithExecutorService {
    private final int port;

    public MainWithExecutorService(int port) {
        this.port = port;
    }

    public void execute() throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on " + serverSocket);
            while (true) {
                System.out.println("Accepting...");
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to " + clientSocket);
                executorService.execute(
                        () -> {
                            try {
                                Service.service(clientSocket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
