import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class BingoGame {
    private int[][] user;
    private int[][] computer;
    public void play(int size) {
        System.out.println("====== 빙고 게임 ======");
        user = makeBoard(size);
        computer = makeBoard(size);
    }

    public int[][] getBoard(boolean isUser) {
        return isUser ? user : computer;
    }

    public boolean[] check(int select) {
        turn(user, select);
        turn(computer, select);
        return new boolean[] {isBingo(user), isBingo(computer)};
    }

    private int[][] makeBoard(int size) {
        int[][] board = new int[size][size];

        List<Integer> list = new java.util.ArrayList<>(IntStream.rangeClosed(1, size * size).boxed().toList());
        Collections.shuffle(list);


        for(int r = 0; r < size; r++)
            for(int c = 0; c < size; c++)
                board[r][c] = list.removeFirst();

        return board;
    }

    private void turn(int[][] board, int select) {
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++) {
                if(board[r][c] == select) {
                    board[r][c] = -1;
                    return;
                }
            }
        }
    }

    private boolean isBingo(int[][] board) {
        final int size = board.length;
        //가로 검사
        for (int[] rows : board) {
            int count = 0;
            for (int col : rows) {
                if(col == -1) count++;
            }
            if (count == size) return true;
        }

        //세로 검사
        for(int c = 0; c < size; c++) {
            int count = 0;
            for (int[] rows : board) {
                if (rows[c] == -1) count++;
            }
            if(count == size) return true;
        }

        //대각선 검사 좌 -> 우
        int count = (int)IntStream.range(0, size).map(i -> board[i][i]).filter(i -> i == -1).count();
        if(count == size) return true;

        return IntStream.range(0, size)
                .map(i -> board[i][size - 1 - i])
                .filter(i -> i == -1)
                .count() == size;
    }

}
