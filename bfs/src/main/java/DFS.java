import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DFS {
    private static final int[][] edges = Edge.edges;
    private static final int VORTEX = Edge.VORTEX;

    private List<Integer>[] adjList;
    private boolean[] visited;

    DFS() {
        adjList = new ArrayList[VORTEX + 1];
        visited = new boolean[VORTEX + 1];

        for(int i = 0; i <= VORTEX; i++)
            adjList[i] = new ArrayList<>();
        createGraph();
    }

    private void createGraph() {
        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }
    }

    public List<String> printGraph() {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < VORTEX + 1; i++) {
            sb.append("Vertex  ").append(i).append(" : ");
            for(int v : adjList[i])
                sb.append(v).append(" ");
            list.add(sb.toString());
            sb.setLength(0);
        }
        return list;
    }

    private void init() {
        Arrays.fill(visited, false);
    }

    public List<Integer> stackDFS(int start) {
        List<Integer> list = new ArrayList<>();

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.addLast(start);
        visited[start] = true;

        while(!stack.isEmpty()) {
            int cur = stack.removeLast();
            list.add(cur);
            for(int next : adjList[cur]) {
                if(!visited[next]) {
                    visited[next] = true;
                    stack.addLast(next);
                }
            }
        }

        init();
        return list;
    }
    public List<Integer> stackDFS() {
        return stackDFS(1);
    }

    public void recursiveDFS(int cur, List<Integer> list) {
        visited[cur] = true;
        list.add(cur);
        for(int next : adjList[cur]) {
            if(!visited[next]) {
                recursiveDFS(next, list);
            }
        }
    }

    public void recursiveDFS(List<Integer> list) {
        recursiveDFS(1, list);
    }
}
