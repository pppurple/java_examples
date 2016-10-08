package bbs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message {
    public static List<Message> messageList = new ArrayList<Message>();

    public String title;
    public String handle;
    public String message;
    public LocalDateTime date;

    Message(String title, String handle, String message) {
        this.title = title;
        this.handle = handle;
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
