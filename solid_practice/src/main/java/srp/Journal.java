package srp;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private final List<String> journal;

    public Journal(ArrayList<String> journal) {
        this.journal = journal;
    }

    public Journal() {
        this.journal = new ArrayList<>();
    }

    public void add(String text) {
        journal.add(text);
    }

    public List<String> getText() {
        return List.copyOf(journal);
    }
}
