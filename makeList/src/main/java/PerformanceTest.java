
public class PerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        final int N = 100_000;
        System.gc();
        Thread.sleep(1000);

        testMyArrayList(N);
        testMyLinkedList(N);
        testDoubleLinkedList(N);
    }

    private static void testMyArrayList(int n) {
        MyArrayList list = new MyArrayList();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        long end = System.nanoTime();
        System.out.println("MyArrayList add: " + ms(start, end) + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.get(i);
        }

        end = System.nanoTime();
        System.out.println("MyArrayList get: " + ms(start, end) + " ms");

        start = System.nanoTime();
        while(!list.isEmpty()){
            list.removeFirst();
        }
        end = System.nanoTime();
        System.out.println("MyArrayList removeFirst: " + ms(start, end) + " ms");
    }

    private static void testMyLinkedList(int n) {
        MyLinkedList list = new MyLinkedList();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.addLast(i);
        }

        long end = System.nanoTime();
        System.out.println("MyLinkedList addLast: " + ms(start, end) + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.get(i);
        }

        end = System.nanoTime();
        System.out.println("MyLinkedList get: " + ms(start, end) + " ms");

        start = System.nanoTime();
        while(!list.isEmpty()){
            list.removeFirst();
        }
        end = System.nanoTime();
        System.out.println("MyLinkedList removeFirst: " + ms(start, end) + " ms");
    }

    private static void testDoubleLinkedList(int n) {
        DoubleLinkedList list = new DoubleLinkedList();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.addLast(i);
        }

        long end = System.nanoTime();
        System.out.println("DoubleLinkedList addLast: " + ms(start, end) + " ms");

        start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.get(i);
        }

        end = System.nanoTime();
        System.out.println("DoubleLinkedList get: " + ms(start, end) + " ms");

        start = System.nanoTime();
        while(!list.isEmpty()){
            list.removeFirst();
        }
        end = System.nanoTime();
        System.out.println("DoubleLinkedList removeFirst: " + ms(start, end) + " ms");
    }

    private static double ms(long start, long end) {
        return (end - start) / 1_000_000.0;
    }
}