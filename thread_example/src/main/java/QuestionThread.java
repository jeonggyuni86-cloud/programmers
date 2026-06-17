public class QuestionThread extends Thread {
    private final Chat chat;
    private final String[] questions;

    QuestionThread(Chat chat) {
        String[] str = { "Hi", "How are you?", "What are you doing?" };
        this(chat, str);
    }
    QuestionThread(Chat chat, String[] questions) {
        this.chat = chat;
        this.questions = questions;
    }

    public void run() {
        for(String q : questions) {
            try {
                chat.question(q);
                Thread.sleep(500);
            } catch(InterruptedException e) {
                break;
            }
        }
    }
}
