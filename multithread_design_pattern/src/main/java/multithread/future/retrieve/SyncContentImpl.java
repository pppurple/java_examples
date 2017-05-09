package multithread.future.retrieve;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;

public class SyncContentImpl implements Content {
    private byte[] contentbytes;

    public SyncContentImpl(String urlstr) {
        System.out.println(Thread.currentThread().getName() + ": Getting " + urlstr);
        try {
            URL url = new URL(urlstr);
            byte[] buffer = new byte[1];
            int index = 0;

            try (DataInputStream in = new DataInputStream(url.openStream())) {
                while (true) {
                    int c = in.readUnsignedByte();
                    if (buffer.length <= index) {
                        byte[] largerbuffer = new byte[buffer.length * 2];
                        System.arraycopy(buffer, 0, largerbuffer, 0, index);
                        buffer = largerbuffer;
                    }
                    buffer[index++] = (byte) c;
                }
            } catch (EOFException ignored) {
            }
            contentbytes = new byte[index];
            System.arraycopy(buffer, 0, contentbytes, 0, index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() {
        return contentbytes;
    }
}
