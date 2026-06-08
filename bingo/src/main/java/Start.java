import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Start {
    static void main(String[] args) {
        BingoGame bingo = new BingoGame();
        Scanner sc = new Scanner(System.in);
        int size = 5;
        bingo.play(size);
        List<Integer> computer = new ArrayList<>(IntStream.rangeClosed(1, size * size).boxed().toList());
        Collections.shuffle(computer);

        int[][] userBoard;
        while(true) {
            userBoard = bingo.getBoard(true);
            for(int[] user : userBoard) {
                for(int u : user) {
                    System.out.printf("%2d ", u);
                }
                System.out.println();
            }

            System.out.print("유저 선택 : ");
            int userPick = userPick(sc);
            if(userPick == -1) {
                System.out.println("다시 선택해주세요");
                continue;
            }
            if(isWinnerContains(bingo.check(userPick))) break;
            int computerPick = computerPick(computer);
            System.out.println("컴퓨터 선택 : " + computerPick + "\n");
            if(isWinnerContains(bingo.check(computerPick))) break;
        }


        int[][] computerBoard = bingo.getBoard(false);

        System.out.println("\n User");
        for(int[] user : userBoard) {
            for(int u : user) {
                System.out.printf("%2d ", u);
            }
            System.out.println();
        }

        System.out.println("\n Computer");

        for(int[] comp: computerBoard) {
            for(int u : comp) {
                System.out.printf("%2d ", u);
            }
            System.out.println();
        }
    }

    private static int userPick(Scanner sc) {
        String str = sc.nextLine();
        return str.chars().allMatch(Character::isDigit) ? Integer.parseInt(str) : -1;
    }

    private static int computerPick(List<Integer> list) {
        return list.removeFirst();
    }

    private static boolean isWinnerContains(boolean[] winner) {
        if(winner[0])
            System.out.println("User Win");
        else if(winner[1])
            System.out.println("Computer Win");
        return winner[0] || winner[1];
    }
}
