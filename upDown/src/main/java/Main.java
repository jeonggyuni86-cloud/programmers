import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        int hard = 0;
        do {
            hard = getHard(sc);
        } while(hard == 0);

        final UpDown updown = new UpDown(hard);
        int count = 0;
        int gap;

        boolean isComplete = false;
        do {
            System.out.println("시도 횟수 : " + ++count);
            System.out.print("숫자를 입력해 주세요 : ");
            gap = updown.isMatch(getNum(sc));

            if(gap > 0) printDown();
            else if(gap < 0) printUp();
            else {
                ok();
                isComplete = true;
            }
        } while(gap != 0 && count < 7);

        System.out.println("시도횟수 : " + count);
        System.out.println("컴퓨터 숫자 : "  + updown.getNum());
        System.out.println(isComplete ? "성공 " : "실패");
    }

    private static int getHard(Scanner sc) {
        System.out.println("난이도를 선택해주세요");
        System.out.println("1. 쉬움 (1 ~ 50)");
        System.out.println("2. 보통 (1 ~ 100)");
        System.out.println("3. 어려움 (1 ~ 300");
        int hard = getNum(sc);

        return switch(hard) {
            case 1 -> 50;
            case 2 -> 100;
            case 3 -> 300;
            default -> 0;
        };
    }

    private static int getNum(Scanner sc) {
        String str = sc.nextLine();
        return str.chars().allMatch(Character::isDigit)
                ? Integer.parseInt(str)
                : 0;
    }

    private static boolean isAvailable(int inbound, int outbound, int num) {
        return num >= inbound && num <= outbound;
    }

    private static void printUp() {
        System.out.println("UP!");
    }

    private static void printDown() {
        System.out.println("Down!");
    }

    private static void ok() {
        System.out.println("정답입니다.");
    }
}
