import java.util.NoSuchElementException;

public class DoubleLinkedList {
    private static class Node {
        Object data;
        Node prev;
        Node next;
        public Node(Object data) {
            this.data = data;
            prev = null;
            next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    public void addFirst(Object data) {
        Node newNode = new Node(data);
        if(head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(Object data) {
        Node newNode = new Node(data);
        if(tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void insert(int index, Object data) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException();
        if(index == 0) {
            this.addFirst(data);
            return;
        } else if(index == size) {
            this.addLast(data);
            return;
        }

        Node nextNode = node(index);
        Node prevNode = nextNode.prev;

        Node newNode = new Node(data);
        newNode.prev = prevNode;
        newNode.next = nextNode;
        prevNode.next = newNode;
        nextNode.prev = newNode;
        size++;
    }

    public Object remove(int index) {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if(index == 0) return removeFirst();
        else if(index == size - 1) return removeLast();

        Node removed = node(index);
        Node nextNode = removed.next;
        Node prevNode = removed.prev;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;

        return removed.data;
    }

    public Object removeFirst() {
        if(head == null) throw new NoSuchElementException();
        Object removed = head.data;
        if(head == tail) {
            //노드 1개인 상황
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return removed;
    }

    public Object removeLast() {
        if(tail == null) throw new NoSuchElementException();
        Object removed = tail.data;
        if(tail == head) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return removed;
    }

    public Object get(int index) {
        return node(index).data;
    }

    public Object getFirst() {
        if(head == null) throw new NoSuchElementException();
        return head.data;
    }
    public Object getLast() {
        if(tail == null) throw new NoSuchElementException();
        return tail.data;
    }

    private Node node(int index) {
        checkIndex(index);
        if(index < size / 2) {
            Node cur = head;
            for(int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur;
        }
        Node cur = tail;
        for(int i = size - 1; i > index; i--) {
            cur = cur.prev;
        }
        return cur;
    }

    private void checkIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
}
