package srp;

public class JournalSaver {
    public void print(Journal journal) {
        for(String s : journal.getText())
            System.out.println(s);
    }
}
