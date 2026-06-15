import java.util.*;

public class MyTreeMap<K extends Comparable<K>, V> implements MyMap<K, V> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    public static class Entry<K, V> {
        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private Node<K, V> root;
    private int size;

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if(node == null) {
            size++;
            return new Node<>(key, value);
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            node.left = put(node.left, key, value);
        } else if(cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = getNode(root, key);
        return node == null ? null : node.value;
    }

    private Node<K, V> getNode(Node<K, V> node, K key) {
        if(node == null) return null;
        int cmp = key.compareTo(node.key);
        if(cmp == 0) return node;
        return cmp > 0 ? getNode(node.right, key) : getNode(node.left, key);
    }

    @Override
    public boolean remove(K key) {
        if(getNode(root, key) == null) return false;
        root = remove(root, key);
        size--;
        return true;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if(node == null) return null;
        int cmp = key.compareTo(node.key);
        if(cmp < 0) node.left = remove(node.left, key);
        else if(cmp > 0) node.right = remove(node.right, key);
        else {
            if(node.left == null && node.right == null) return null;
            if(node.left == null) return node.right;
            if(node.right == null) return node.left;

            Node<K, V> next = findMin(node.right);
            node.key = next.key;
            node.value = next.value;
            node.right = remove(node.right, node.key);
        }
        return node;
    }

    private Node<K, V> findMin(Node<K, V> node) {
        while(node.left != null) node = node.left;
        return node;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySet(root, set);
        return set;
    }

    private void keySet(Node<K, V> node, Set<K> set) {
        if(node == null) return;
        keySet(root.left, set);
        set.add(node.key);
        keySet(root.right, set);
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        values(root, list);
        return list;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new LinkedHashSet<>();
        entrySet(root, set);
        return set;
    }

    private void entrySet(Node<K, V> node, Set<Entry<K, V>> set) {
        if(node == null) return;
        entrySet(node.left, set);
        set.add(new Entry<>(node.key, node.value));
        entrySet(node.right, set);
    }

    private void values(Node<K, V> node, List<V> list) {
        if(node == null) return;
        values(node.left, list);
        list.add(node.value);
        values(node.right, list);
    }

}
