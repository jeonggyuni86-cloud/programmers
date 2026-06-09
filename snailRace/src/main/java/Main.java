import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Snail> snails = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        int num = print("달팽이 마리수 입력 : ", sc);
        int len = print("트랙 길이 입력 : ", sc);
        int speed = print("달팽이 속도 입력 : ", sc);

        Race race = new Race();
        for(int i = 0; i < num; i++) {
            Snail snail = new Snail("달팽이 " + (i + 1), race, len, speed);
            snails.add(snail);
            snail.start();
        }

        for(Snail snail : snails) {
            try {
                snail.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        race.printRanking();


    }
    private static int getInput(Scanner sc) {
        String str = sc.nextLine();
        return str.chars().allMatch(Character::isDigit)
                ? Integer.parseInt(str)
                : 0;
    }

    private static int print(String msg, Scanner sc) {
        int num;
        do {
            System.out.print(msg);
            num = getInput(sc);
        } while(num == 0);
        return num;
    }
}
