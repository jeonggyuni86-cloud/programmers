import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Race {
    private int rank = 1;
    private final List<String> ranking = new ArrayList<>();
    private final Map<String, Integer> positions = new LinkedHashMap<>();

    public synchronized int finish(String name) {
        ranking.add(name);
        return rank++;
    }

    public void printRanking() {
        for(int i = 0; i < ranking.size(); i++) {
            System.out.println(i + 1 + "등 : " + ranking.get(i));
        }
    }

    public synchronized void printProgress(String name, int position) {
        positions.put(name, position);

        clearConsole();

        for (String snail : positions.keySet()) {
            int pos = positions.get(snail);
            System.out.println(snail + " : " + "=".repeat(pos) + "> " + pos);
        }
    }

    private void clearConsole() {
        System.out.println("\n".repeat(50));
    }
}
