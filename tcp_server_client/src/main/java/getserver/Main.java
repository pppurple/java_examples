package getserver;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pppurple on 2016/10/01.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try (ServerSocket server = new ServerSocket(8080)) {
            for (;;) {
                Socket socket = server.accept();
                ServerThread serverThread = new ServerThread(socket);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        }

    }
}
