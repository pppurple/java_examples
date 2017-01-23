package ftpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;

public class FtpServerThread implements Runnable {
    private static String separator; // file separator
    private static String dir;
    private Socket client;

    static {
        separator = System.getProperty("file.separator");
        dir = System.getProperty("user.dir");
    }

    FtpServerThread(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            int ip = -1;

            // clien IPアドレス取得
            InetAddress clientInet = client.getInetAddress();
            String hostWithMask = clientInet.toString();
            System.out.println("hostWithMask:" + hostWithMask);
            int idx = hostWithMask.indexOf("/");
//            String host = hostWithMask.substring(idx + 1);
            String host = clientInet.getHostAddress();
            System.out.println("host:" + host);

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("220 Service ready for new user.");

            while(true){

                String line = in.readLine();
                System.out.println("[DEBUG]host: " + host + ", line: " + line) ;

                if (line == null){
                    break;
                }
                if (line.startsWith("USER")){
                    out.println("331 User name okay, need password.");
                }
                if (line.startsWith("PASS")){
                    out.println("230 User logged in proceed.");
                }
                if (line.startsWith("SYST")){
                    out.println("215 NAME system type.");
                }
                if (line.startsWith("TYPE")){
                    out.println("200 TYPE Command okay.");
                }

                if (line.startsWith("PORT")){
                    out.println("200 PORT Command okay.");
                    String a1 = "";
                    String a2 = "";
                    int lng = line.length() - 1;
                    int lng2 = line.lastIndexOf(",");
                    int lng1 = line.lastIndexOf(",", lng2 - 1);
                    for (int i = lng1 + 1; i < lng2; i++) {
                        a1 = a1 + line.charAt(i);
                    }
                    for (int i = lng2 + 1; i <= lng; i++) {
                        a2 = a2 + line.charAt(i);
                    }
                    int ip1 = Integer.parseInt(a1);
                    int ip2 = Integer.parseInt(a2);
                    ip = ip1 * 16 * 16 + ip2;
                }

                if (line.startsWith("STOR")) {
                    out.println("150 File status okay; about to open data connection.");
                    String file = line.substring(4).trim();
                    System.out.println("file:" + file);
                    System.out.println("dir:" + dir);
                    RandomAccessFile inFile = new RandomAccessFile(dir + separator + file, "rw");
                    Socket sock = new Socket(host, ip);
                    InputStream inStor = sock.getInputStream();
                    byte bb[] = new byte[1024];
                    int amount;
                    try {
                        while ((amount = inStor.read(bb)) != -1) {
                            inFile.write(bb, 0, amount);
                        }
                        inStor.close();
                        out.println("226 Closing data connection.");
                        inFile.close();
                        sock.close();
                    } catch (IOException e) {
                        System.out.println("[ERROR] cause:" + e.getMessage());
                    }
                }
                // 拡張機能の設定
                if (line.startsWith("OPTS")){
                    out.println("200 UTF8 set to on.\r");
                }
                if (line.startsWith("QUIT")) {
                    out.println("Goodbye");
                    break;
                }
            }

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
