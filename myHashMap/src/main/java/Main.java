import java.util.HashMap;
import java.util.Map;

public class Main {
    static void main(String[] args) {
        final MyHashMap<Integer, Integer> map = new MyHashMap<>();
        final Map<Integer, Integer> map2 = new HashMap<>();

        map.put(0, 1);
        map.put(16, 3);

        System.out.println(map.get(0));
        System.out.println(map.get(16));
    }
}
