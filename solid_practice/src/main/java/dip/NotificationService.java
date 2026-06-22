package dip;

public class NotificationService {
    private final MessageSender messageSender;
    public NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void notifyUser(String msg) {
        messageSender.send(msg);
    }
}
