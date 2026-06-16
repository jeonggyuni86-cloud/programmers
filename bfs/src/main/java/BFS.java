import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BFS {
    private static final int[][] edges = Edge.edges;
    private static final int VERTEX = Edge.VERTEX;

    private final List<Integer>[] graph;
    BFS() {
        graph = new List[VERTEX + 1];
        for(int i = 0; i < VERTEX + 1; i++)
            graph[i] = new ArrayList<>();
        createGraph();
    }

    private void createGraph() {
        for(int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
    }

    public List<String> printGraph() {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < VERTEX + 1; i++) {
            sb.append("Vertex  ").append(i).append(" : ");
            for(int v : graph[i])
                sb.append(v).append(" ");
            list.add(sb.toString());
            sb.setLength(0);
        }
        return list;
    }


    public List<Integer> bfs(int start) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[VERTEX + 1];
        queue.add(start);
        visited[start] = true;

        List<Integer> list = new ArrayList<>();

        while(!queue.isEmpty()) {
            int cur = queue.removeFirst();
            list.add(cur);

            for(int next : graph[cur]) {
                if(!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }

        return list;
    }

    public List<Integer> bfs() {
        return bfs(1);
    }

}

