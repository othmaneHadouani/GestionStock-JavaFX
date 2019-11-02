package notification;

import java.util.ResourceBundle;

public class Message {
    ResourceBundle resourceBundle;
    private static Message message;

    public String getMessage(String s) {
        return resourceBundle.getString(s);
    }

    public static Message get() {
        if (message == null)
            message = new Message();
        return message;
    }

    private Message() {
        resourceBundle = ResourceBundle.getBundle("properties.config");
    }

}
