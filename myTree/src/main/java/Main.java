
public class Main {
    static void main(String[] args) {
        final MyTree<Integer> tree = new MyTree<>();

        for(int num : new int[]{20, 10, 26, 8, 22, 28, 9, 37})
            tree.insert(num);

        System.out.println("트리 높이 : " + tree.height());
        System.out.println("트리 모양");
        tree.printTree().forEach(System.out::println);
        System.out.print("\n".repeat(2));

        System.out.println("전위 순회");
        for(int num : tree.preorder())
            System.out.printf("%d ", num);
        System.out.print("\n".repeat(2));

        System.out.println("후위 순회");
        for(int num : tree.postorder())
            System.out.printf("%d ", num);
        System.out.print("\n".repeat(2));

        System.out.println("중위 순회");
        for(int num : tree.inorder())
            System.out.printf("%d ", num);
        System.out.print("\n".repeat(2));

        System.out.println("BFS");
        for(int num : tree.bfs())
            System.out.printf("%d ", num);
        System.out.print("\n".repeat(2));

        System.out.println("DFS");
        for(int num : tree.dfs())
            System.out.printf("%d ", num);
        System.out.print("\n".repeat(2));

        System.out.println("Recursive DFS");
        for(int num : tree.recursiveDFS())
            System.out.printf("%d ", num);
        System.out.print("\n".repeat(2));

        System.out.println("37 포함 여부 : " + tree.contains(37));
        System.out.println("노드 개수 : " + tree.countNodes());
        System.out.println("리프 노드 개수 : " + tree.countLeaves());
    }
}
