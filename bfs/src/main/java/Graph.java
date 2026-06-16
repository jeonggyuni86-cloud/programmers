import java.util.*;

public class Graph<T> {
    private final Map<T, List<T>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addEdge(T from, T to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        graph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
    }

    public void addEdge(T[][] edges) {
        for(T[] edge : edges) addEdge(edge[0], edge[1]);
    }

    public void addVertex(T vertex) {
        graph.computeIfAbsent(vertex, k -> new ArrayList<>());
    }

    public List<T> bfs(T start) {
        Set<T> visited = new HashSet<>();
        ArrayDeque<T> queue = new ArrayDeque<>();
        List<T> result = new ArrayList<>();

        queue.addLast(start);
        visited.add(start);

        while(!queue.isEmpty()){
            T current = queue.removeFirst();
            result.add(current);

            for(T next : graph.getOrDefault(current, Collections.emptyList())){
                if(visited.contains(next)) continue;
                queue.addLast(next);
                visited.add(next);
            }
        }

        return result;
    }

    public List<T> dfs(T start) {
        Set<T> visited = new HashSet<>();
        ArrayDeque<T> stack = new ArrayDeque<>();
        List<T> result = new ArrayList<>();
        stack.addLast(start);
        visited.add(start);

        while(!stack.isEmpty()){
            T current = stack.removeLast();
            result.add(current);

            for(T next : graph.getOrDefault(current, Collections.emptyList())){
                if(visited.contains(next)) continue;
                stack.addLast(next);
                visited.add(next);
            }
        }
        return result;
    }
}
