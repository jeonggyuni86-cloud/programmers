import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DFS {
    private static final int[][] edges = Edge.edges;
    private static final int VERTEX = Edge.VERTEX;

    private final List<Integer>[] adjList;
    private final boolean[] visited;

    DFS() {
        adjList = new ArrayList[VERTEX + 1];
        visited = new boolean[VERTEX + 1];

        for(int i = 0; i <= VERTEX; i++)
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
        for(int i = 1; i < VERTEX + 1; i++) {
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

    private void stackDFS(int start, List<Integer> list) {
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
    }
    public List<Integer> stackDFS(int start) {
        init();
        List<Integer> list = new ArrayList<>();
        stackDFS(start, list);
        return list;
    }

    public List<Integer> stackDFS() {
        return stackDFS(1);
    }

    private void recursiveDFS(int cur, List<Integer> list) {
        visited[cur] = true;
        list.add(cur);
        for(int next : adjList[cur]) {
            if(!visited[next]) {
                recursiveDFS(next, list);
            }
        }
    }
    public List<Integer> recursiveDFS(int start) {
        init();
        List<Integer> list = new ArrayList<>();
        recursiveDFS(start,list);
        return list;
    }
    public List<Integer> recursiveDFS() {
        return recursiveDFS(1);
    }
}
