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
    public void showAccount(String date) {
        int price = 0;
        List<Item> list = map.getOrDefault(date, Collections.emptyList());
        for(int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s : %d\n", i + 1, list.get(i).name(), list.get(i).price());
            price += list.get(i).price();
        }
        System.out.println("합계 : " + price);
    }

    @Override
    public void deleteItem(String date, int idx) {
        map.get(date).remove(idx);
    }

}
