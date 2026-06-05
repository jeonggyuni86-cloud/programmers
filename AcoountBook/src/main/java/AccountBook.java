import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface AccountBook {
    Map<String, List<Item>> map = new TreeMap<>();
    void addAccount(String date, Item item);
    void showAccount();
    void deleteAll(String date);
    void deleteItem(String date, int idx);
}
