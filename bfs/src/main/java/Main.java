import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) {
        BFS bfs = new BFS();

        System.out.println("========= BFS ========");
        for(String str : bfs.printGraph())
            System.out.println(str);
        for(int num : bfs.bfs())
            System.out.print(num + " ");

        DFS dfs = new DFS();

        System.out.println("\n\n========= DFS ========");
        System.out.println("STACK");
        for(int num : dfs.stackDFS())
            System.out.print(num + " ");
        System.out.println("\n\nRECURSIVE");

        List<Integer> list = new ArrayList<>();
        dfs.recursiveDFS(list);
        for(int num : list)
            System.out.print(num + " ");

    }
}
