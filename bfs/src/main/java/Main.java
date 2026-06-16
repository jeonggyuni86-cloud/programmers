public class Main {
    static void main(String[] args) {
        BFS bfs = new BFS();


        for(String str : bfs.printGraph())
            System.out.println(str);
        System.out.println("\n\n========= BFS ========");
        for(int num : bfs.bfs())
            System.out.print(num + " ");

        DFS dfs = new DFS();

        System.out.println("\n\n========= DFS ========");
        System.out.println("STACK");
        for(int num : dfs.stackDFS())
            System.out.print(num + " ");

        System.out.println("\n\nRECURSIVE");
        for(int num : dfs.recursiveDFS())
            System.out.print(num + " ");

        System.out.println();

    }
}
