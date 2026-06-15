import java.util.*;

public class MyTree <T extends Comparable<T>> {
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root;
    private int size;
    public MyTree() {
        root = null;
        size = 0;
    }

    public void insert(T value) {
        root = this.insert(root, value);
    }

    private Node<T> insert(Node<T> node, T value) {
        if(node == null) {
            size++;
            return new Node<>(value);
        }
        int cmp = value.compareTo(node.value);
        if(cmp < 0) {
            node.left = insert(node.left, value);
        } else if(cmp > 0) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    public boolean contains(T value) {
        return this.contains(root, value);
    }

    private boolean contains(Node<T> node, T value) {
        if (node == null) return false;
        int cmp = value.compareTo(node.value);
        if (cmp == 0) return true;
        else if (cmp < 0) return contains(node.left, value);
        else return contains(node.right, value);
    }

    public int countNodes() {
        return this.countNodes(root);
    }
    public int countLeaves() {
        return this.countLeaves(root);
    }

    private int countNodes(Node<T> node) {
        if(node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private int countLeaves(Node<T> node) {
        if(node == null) return 0;
        if(node.left == null && node.right == null) return 1;
        return countLeaves(node.left) + countLeaves(node.right);
    }

    public List<String> printTree() {
        List<String> lines = new ArrayList<>();
        printTree(root, 0, lines);
        return lines;
    }

    private void printTree(Node<T> node, int depth, List<String> lines) {
        if (node == null) return;

        printTree(node.right, depth + 1, lines);

        lines.add("    ".repeat(depth) + node.value);

        printTree(node.left, depth + 1, lines);
    }

    public List<T> preorder() {
        List<T> nodes = new ArrayList<>();
        preOrder(root, nodes);
        return nodes;
    }

    private void preOrder(Node<T> node, List<T> list) {
        if(node == null) return;
        list.add(node.value);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }

    public List<T> inorder() {
        List<T> nodes = new ArrayList<>();
        inorder(root, nodes);
        return nodes;
    }

    private void inorder(Node<T> node, List<T> list) {
        if(node == null) return;
        inorder(node.left, list);
        list.add(node.value);
        inorder(node.right, list);
    }

    public List<T> postorder() {
        List<T> nodes = new ArrayList<>();
        postorder(root, nodes);
        return nodes;
    }

    private void postorder(Node<T> node, List<T> list) {
        if(node == null) return;
        postorder(node.left, list);
        postorder(node.right, list);
        list.add(node.value);
    }

    public int size() {
        return this.size;
    }

    public List<T> bfs() {
        final ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        final Set<Node<T>> visited = new HashSet<>();
        init(queue, visited);

        final List<T> list = new ArrayList<>();

        while (!queue.isEmpty()) {
            Node<T> cur = queue.removeFirst();
            list.add(cur.value);
            if(cur.left != null && !visited.contains(cur.left)) {
                queue.addLast(cur.left);
                visited.add(cur.left);
            }
            if(cur.right != null && !visited.contains(cur.right)) {
                queue.addLast(cur.right);
                visited.add(cur.right);
            }
        }
        return list;
    }

    public List<T> dfs() {
        ArrayDeque<Node<T>> stack = new ArrayDeque<>();
        Set<Node<T>> visited = new HashSet<>();
        this.init(stack, visited);

        final List<T> list = new ArrayList<>();

        while(!stack.isEmpty()) {
            Node<T> cur = stack.removeLast();
            list.add(cur.value);
            if(cur.left != null && !visited.contains(cur.left)) {
                stack.addLast(cur.left);
                visited.add(cur.left);
            }
            if(cur.right != null && !visited.contains(cur.right)) {
                stack.addLast(cur.right);
                visited.add(cur.right);
            }
        }

        return list;
    }

    public List<T> recursiveDFS() {
        List<T> list = new ArrayList<>();
        Set<Node<T>> visited = new HashSet<>();
        recursiveDFS(root, list, visited);
        return list;
    }

    private void recursiveDFS(Node<T> node, List<T> list, Set<Node<T>> visited) {
        if(node == null || visited.contains(node)) return;
        visited.add(node);
        list.add(node.value);

        recursiveDFS(node.right, list, visited);
        recursiveDFS(node.left, list, visited);
    }
    private void init(ArrayDeque<Node<T>> deque, Set<Node<T>> visited) {
        if(root == null) return;
        deque.addLast(root);
        visited.add(root);
    }
}
