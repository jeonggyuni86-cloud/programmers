package dip;

public class EmailSender implements MessageSender {
    @Override
    public void send(String msg) {
        System.out.println("[EMAIL] " + msg);
    }
}
