package chat;

public class Main {
    static void main(String[] args) {
        Chat chat = new Chat();
        AnswerThread answerThread = new AnswerThread(chat);
        QuestionThread questionThread = new QuestionThread(chat);

        answerThread.start();
        questionThread.start();
    }
}
