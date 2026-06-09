import java.util.ArrayList;
import java.util.List;

public class Race {
    private int rank = 1;
    private final List<String> ranking = new ArrayList<>();

    public synchronized int finish(String name) {
        ranking.add(name);
        return rank++;
    }
    public void printRanking() {
        for(int i = 0; i < ranking.size(); i++) {
            System.out.println(i + 1 + "등 : " + ranking.get(i));
        }
    }
}
