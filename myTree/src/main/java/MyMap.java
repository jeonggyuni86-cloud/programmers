import java.util.Collection;
import java.util.Set;

public interface MyMap<K, V>{
    void put(K key, V value);
    V get(K key);
    boolean remove(K key);
    boolean containsKey(K key);
    int size();
    boolean isEmpty();
    Set<K> keySet();
    Collection<V> values();
    Set<MyTreeMap.Entry<K, V>> entrySet();
    interface Entry<K, V> {
        K key();
        V value();
    }
}
