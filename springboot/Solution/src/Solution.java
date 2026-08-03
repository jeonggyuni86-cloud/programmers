import java.util.ArrayDeque;
import java.util.Arrays;

class Solution {
    public int solution(int[][] maps) {
        return bfs(maps);
    }

    private int bfs(int[][] maps) {
        final var rows = maps.length;
        final var cols = maps[0].length;
        final var dir = new int[] {
                pack(-1, 0, 2),
                pack(1,  0, 2),
                pack(0, -1, 2),
                pack(0,  1, 2)
        };
        var queue = new ArrayDeque<Integer>();
        var dist = new int[rows * cols];
        var start = pack(0, 0, cols);

        Arrays.fill(dist, -1);
        queue.offer(start);
        dist[start] = 1;
        while (!queue.isEmpty()) {
            var cur = queue.poll();
            var unpacked = unpack(cur, cols);
            for (var j : dir) {
                var d = unpack(j, 2);
                var nr = unpacked[0] + d[0];
                var nc = unpacked[1] + d[1];
                if (!canMove(nr, nc, rows, cols)) continue;
                var next = pack(nr, nc, cols);
                if (dist[next] == -1 && maps[nr][nc] == 1) {
                    dist[next] = dist[cur] + 1;
                    queue.offer(next);
                }
            }
        }
        return dist[pack(rows - 1, cols - 1, cols)];
    }

    private int pack(int r, int c, int bias) {
        return r * bias + c;
    }

    private int[] unpack(int packed, int bias) {
        return new int[] {packed / bias, packed % bias};
    }

    private boolean canMove(int nr, int nc, int rows, int cols) {
        return nr >= 0 && nr < rows && nc >= 0 && nc < cols;
    }
}