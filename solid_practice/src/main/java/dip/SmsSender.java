package dip;

public class SmsSender implements MessageSender {
    @Override
    public void send(String msg) {
        System.out.println("[SMS] " + msg);
    }
}
