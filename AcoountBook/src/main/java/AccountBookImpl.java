import java.util.*;

public class AccountBookImpl implements AccountBook{
    private final Map<String, List<Item>> map;
    AccountBookImpl() {
        map = new TreeMap<>();
    }
    @Override
    public void addAccount(String date, Item item) {
        map.computeIfAbsent(date, k -> new ArrayList<>()).add(item);
    }


    @Override
    public void deleteAll(String date) {
        map.remove(date);
    }

    @Override
    public void showAccount() {
        int idx = 0;
        for(String key : map.keySet()) {
            System.out.println(++idx + ". " + key);
        }
    }
    public void showAccount(String date) {
        List<Item> list = map.getOrDefault(date, Collections.emptyList());
        int price = 0;
        for(int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s : %d\n", i + 1, list.get(i).name(), list.get(i).price());
            price += list.get(i).price();
        }
        System.out.println("총액 : " + price);
    }


    @Override
    public void deleteItem(String date, int idx) {
        map.get(date).remove(idx);
        if(map.get(date).isEmpty()) map.remove(date);
    }

}
