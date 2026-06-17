package chat;

public class AnswerThread extends Thread {
    private final Chat chat;
    private final String[] answers;

    AnswerThread(Chat chat) {
        String[] str = {"Hello", "I'm fine, thank you!", "I'm coding in Java"};
        this(chat, str);
    }

    AnswerThread(Chat chat, String[] answers) {
        this.chat = chat;
        this.answers = answers;
    }

    public void run() {
        for(String a : answers) {
            try {
                chat.answer(a);
                Thread.sleep(500);
            } catch(InterruptedException e) {
                break;
            }
        }
    }
}
