package bbsservlet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message {
    public static List<Message> messageList = new ArrayList<Message>();

    String title;
    String handle;
    String message;
    LocalDateTime date;

    Message(String title, String handle, String message) {
        this.title = title;
        this.handle = handle;
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
