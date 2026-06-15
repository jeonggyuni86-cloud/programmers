public class Main {
    static void main(String[] args) {
        final MyTree<Integer> tree = new MyTree<>();

        for(int num : new int[]{20, 10, 26, 8, 22, 28, 5, 9})
            tree.insert(num);

        System.out.println("전위 순회");
        for(int num : tree.preorder())
            System.out.printf("%d ", num);
        System.out.println();

        System.out.println("후위 순회");
        for(int num : tree.postorder())
            System.out.printf("%d ", num);
        System.out.println();

        System.out.println("중위 순회");
        for(int num : tree.inorder())
            System.out.printf("%d ", num);
        System.out.println();
    }

}
