public class Main {
    static void main(String[] args) {
        final MyHashMap<Integer, Integer> map = new MyHashMap<>();

        System.out.println(map.getOrDefault(1, 30));
    }
}
