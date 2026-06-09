import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Race {
    private record Result(
            String name,
            long time) { }

    private final List<Result> ranking;
    private final Map<String, Integer> positions;
    private final long startTime;

    public Race() {
        this.startTime = System.currentTimeMillis();
        this.ranking = new ArrayList<>();
        this.positions = new LinkedHashMap<>();
    }

    public synchronized void finish(String name) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        ranking.add(new Result(name, elapsedTime));
    }

    public void printRanking() {
        for(int i = 0; i < ranking.size(); i++) {
            Result result = ranking.get(i);
            System.out.printf("%d등. %S (%.3f s)\n", i + 1, result.name, result.time() / 1000.0);
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
