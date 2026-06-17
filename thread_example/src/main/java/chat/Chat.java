package chat;

public class Chat {
    Turn turn = Turn.QUESTION;

    enum Turn {
        QUESTION,
        ANSWER;
    }

    public synchronized void question(String msg) throws InterruptedException {
        while(turn != Turn.QUESTION) { wait();}
        System.out.println("Q: " + msg);
        turn = Turn.ANSWER;
        notifyAll();
    }

    public synchronized void answer(String msg) throws InterruptedException {
        while(turn != Turn.ANSWER) { wait();}
        System.out.println("A: " + msg);
        turn = Turn.QUESTION;
        notifyAll();
    }
}
