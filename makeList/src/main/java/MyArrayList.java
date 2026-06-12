import java.util.Arrays;

public class MyArrayList {
    private Object[] data;
    private int size;

    public MyArrayList(int capacity) {
        data = new Object[capacity];
        size = 0;
    }

    public MyArrayList() {
        data = new Object[10];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(Object o) {
        if (size == data.length)
            resize();
        data[size++] = o;
    }

    public void resize() {
        Object[] newArr = new Object[data.length * 2];
        System.arraycopy(data, 0, newArr, 0, size);
        data = newArr;
    }

    public Object get(int index) {
        if (index >= size || index < 0)
            return null;
        return data[index];
    }
    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size - 1);
    }

    public void set(int index, Object o) {
        if (index >= size || index < 0)
            return;
        data[index] = o;
    }

    public Object remove(int index) {
        if (index >= size || index < 0)
            return null;
        Object obj = data[index];
        for (int i = index; i < size - 1; i++)
            data[i] = data[i + 1];
        return obj;
    }

    public Object removeFirst() {
        return remove(0);
    }

    public Object removeLast() {
        return remove(size - 1);
    }


}
