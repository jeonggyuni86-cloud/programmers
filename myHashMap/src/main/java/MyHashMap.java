public class MyHashMap<K, V> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @SuppressWarnings("unchecked")
    private final Node<K, V>[] table = new Node[16];
    private int size = 0;

    public void put(K key, V value) {
        int idx = index(key);
        for(Node<K, V> cur = table[idx]; cur != null; cur = cur.next) {
            if(cur.key.equals(key)) {
                cur.value = value;
                return;
            }
        }
        table[idx] = new Node<>(key, value, table[idx]);
        size++;
    }

    public V get(K key) {
        int idx = index(key);
        for(Node<K, V> cur = table[idx]; cur != null; cur = cur.next) {
            if(cur.key.equals(key)) {
                return cur.value;
            }
        }
        return null;
    }

    public V getOrDefault(K key, V defaultValue) {
        return this.containsKey(key) ? this.get(key) : defaultValue;
    }

    public V remove(K key) {
        int idx = index(key);
        Node<K, V> prev = null;
        Node<K, V> cur = table[idx];

        while(cur != null) {
            if(cur.key.equals(key)) {
                if(prev == null) {
                    table[idx] = table[idx].next;
                } else {
                    prev.next = cur.next;
                }
                size--;
                return cur.value;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        int idx = index(key);
        for(Node<K, V> cur = table[idx]; cur != null; cur = cur.next) {
            if(cur.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private int index(K key) {
        return Math.abs(key.hashCode() & 0x7fffffff) % table.length;
    }

}
