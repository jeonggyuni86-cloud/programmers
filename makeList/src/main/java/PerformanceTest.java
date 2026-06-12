import java.util.concurrent.ThreadLocalRandom;

public class PerformanceTest {
    public static void main(String[] args) throws InterruptedException {
        final int N = 100_000;

        int[] indexes = new int[N];

        for (int i = 0; i < N; i++) {
            indexes[i] = ThreadLocalRandom.current().nextInt(i + 1);
        }

        System.gc();
        Thread.sleep(1000);

        testMyArrayList(N, indexes);
        testMyLinkedList(N, indexes);
        testDoubleLinkedList(N, indexes);
    }

    private static void testMyArrayList(int n, int[] indexes) {
        MyArrayList list = new MyArrayList();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.insert(indexes[i], i);
        }

        long end = System.nanoTime();
        System.out.println("ArrayList random insert: " + ms(start, end) + " ms");
    }

    private static void testMyLinkedList(int n, int[] indexes) {
        MyLinkedList list = new MyLinkedList();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.insert(indexes[i], i);
        }

        long end = System.nanoTime();
        System.out.println("LinkedList random insert: " + ms(start, end) + " ms");
    }

    private static void testDoubleLinkedList(int n, int[] indexes) {
        DoubleLinkedList list = new DoubleLinkedList();

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            list.insert(indexes[i], i);
        }

        long end = System.nanoTime();
        System.out.println("DoubleLinkedList random insert: " + ms(start, end) + " ms");
    }

    private static double ms(long start, long end) {
        return (end - start) / 1_000_000.0;
    }
}