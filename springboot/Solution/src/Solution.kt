import java.util.PriorityQueue

class Solution {
    data class Edge(
        val to: Int,
        val cost: Int
    )

    fun solution(n: Int, costs: Array<IntArray>) = prim(0, n, createGraph(costs))

    fun createGraph(costs: Array<IntArray>): Map<Int, List<Edge>> {
        val graph = mutableMapOf<Int, MutableList<Edge>>()

        for (cost in costs) {
            graph.getOrPut(cost[0]) { mutableListOf() }.add(Edge(cost[1], cost[2]))
            graph.getOrPut(cost[1]) { mutableListOf() }.add(Edge(cost[0], cost[2]))
        }

        return graph
    }

    fun prim(start: Int, n: Int, graph: Map<Int, List<Edge>>): Int {
        val pq = PriorityQueue(Comparator.comparingInt(Edge::cost))
        val visited = BooleanArray(n)

        var totalPrice = 0
        var count = 0
        pq.add(Edge(start, 0))

        while (pq.isNotEmpty()) {
            val cur = pq.poll()

            if (visited[cur.to]) continue

            visited[cur.to] = true
            totalPrice += cur.cost

            if (++count == n) return totalPrice

            for (next in graph[cur.to] ?: listOf()) {
                if (!visited[next.to]) {
                    pq.add(next)
                }
            }
        }

        return totalPrice
    }
}