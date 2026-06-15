public class Main {
    static void main(String[] args) {
        final MyTree<Integer> tree = new MyTree<>();
        final int[] arr = new int[] {10, 70, 40, 20, 30, 60, 80};

        /*
        for(int num : arr)
            tree.insert(num);
        int idx = 0;
        while(!tree.isEmpty()) {
            System.out.println("=============================");
            System.out.println("트리 높이 : " + tree.height());
            System.out.println("트리 모양");
            tree.printTree().forEach(System.out::println);
            System.out.print("\n".repeat(2));

            System.out.println("전위 순회");
            for (int num : tree.preorder())
                System.out.printf("%d ", num);
            System.out.print("\n".repeat(2));

            System.out.println("후위 순회");
            for (int num : tree.postorder())
                System.out.printf("%d ", num);
            System.out.print("\n".repeat(2));

            System.out.println("중위 순회");
            for (int num : tree.inorder())
                System.out.printf("%d ", num);
            System.out.print("\n".repeat(2));

            System.out.println("BFS");
            for (int num : tree.bfs())
                System.out.printf("%d ", num);
            System.out.print("\n".repeat(2));

            System.out.println("DFS");
            for (int num : tree.dfs())
                System.out.printf("%d ", num);
            System.out.print("\n".repeat(2));

            System.out.println("Recursive DFS");
            for (int num : tree.recursiveDFS())
                System.out.printf("%d ", num);
            System.out.print("\n".repeat(2));

            System.out.println("노드 개수 : " + tree.countNodes());
            System.out.println("리프 노드 개수 : " + tree.countLeaves());
            System.out.println("삭제 : " + arr[idx]);
            tree.remove(arr[idx++]);
        }

         */

        /* ================================================================================= */

        System.out.print("\n".repeat(2));
        final MyMap<String, Integer> map = new MyTreeMap<>();
        final String[] names = new String[] {"ㄴ", "ㄱ", "ㄷ"};
        final int[] scores = new int[] {10, 30, 40};

        for(int i = 0; i < names.length; i++)
            map.put(names[i], scores[i]);

        for(MyMap.Entry<String, Integer> entry : map.entrySet())
            System.out.println(entry);

    }
}
