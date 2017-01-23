package ftpserver;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 8821;
    private static final int SO_TIMEOUT = 0; // msec

    public static void main(String[] args) throws Exception {
        try(ServerSocket server = new ServerSocket(PORT)) {
            server.setSoTimeout(SO_TIMEOUT);

            System.out.println("[INFO]FTP Server started. PORT:" + PORT);

            for(;;){
                Socket client = server.accept();
                FtpServerThread ftpServerThread = new FtpServerThread(client);
                new Thread(ftpServerThread).start();
            }
        }
    }
}
