import java.util.NoSuchElementException;

public class MyLinkedList {


    private static class Node {
        Object data;
        Node next;
        Node(Object data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    public MyLinkedList() {
        head = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }

    public void addFirst(Object data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void addLast(Object data) {
        Node newNode = new Node(data);
        if(head == null) {
            head = newNode;
            size++;
            return;
        }

        Node cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = newNode;
        size++;
    }

    public Object get(int index) {
        checkIndex(index);
        Node cur = head;

        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    public Object removeFirst() {
        if(head == null) throw new NoSuchElementException();
        Object removed = head.data;
        head = head.next;
        size--;
        return removed;
    }

    public Object removeLast() {
        if(head == null) throw new NoSuchElementException();
        if(head.next == null) {
            Object removed = head.data;
            head = null;
            size--;
            return removed;
        }
        Node cur = head;
        while(cur.next.next != null) {
            cur = cur.next;
        }

        Object removed = cur.next.data;
        cur.next = null;
        size--;
        return removed;
    }

    public void insert(int index, Object data) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException();
        if(index == 0) {
            addFirst(data);
            return;
        }
        Node cur = head;
        for(int i = 0; i < index - 1; i++)
            cur = cur.next;

        Node newNode = new Node(data);

        newNode.next = cur.next;
        cur.next = newNode;

        size++;
    }

    private void checkIndex(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
    }

    public void printLinks() {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }

        System.out.println();
    }

}
