public class Main {
    static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();


        System.out.println("================= LinkedList =================");
        // --- Step 3 + 4 확인 ---
        list.addLast("가");
        list.addLast("나");
        list.addLast("다");
        System.out.print("addLast 후: ");
        list.printLinks();
        // 기대: [null <- 가 -> 나] [가 <- 나 -> 다] [나 <- 다 -> null]

        // --- Step 5 확인 ---        list.addFirst("앞");
        System.out.print("addFirst 후: ");
        list.printLinks();
        // 기대: [null <- 앞 -> 가] [앞 <- 가 -> 나] [가 <- 나 -> 다] [나 <- 다 -> null]

        System.out.println("get(2) = " + list.get(2));   // 기대: 나

        list.insert(2, "끼움");
        System.out.print("insert 후: ");
        list.printLinks();

        System.out.println(list.size());
    }
}

