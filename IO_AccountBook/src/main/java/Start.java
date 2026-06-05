import java.time.LocalDate;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final AccountBook accountBook = new AccountBookImpl();

        while (true) {
            String date = String.valueOf(LocalDate.now());

            System.out.println("====== 가계부(File) ======");
            System.out.println("1. 내역 추가");
            System.out.println("2. 내역 조회");
            System.out.println("3. 삭제");
            System.out.println("4. 종료");

            int menu = inputNumber(sc);

            switch (menu) {
                case 1:
                    while(addAccount(sc, accountBook, date)) {}
                    break;

                case 2:
                    showAccount(sc, accountBook);
                    break;

                case 3:
                    deleteAccount(sc, accountBook);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    private static boolean addAccount(Scanner sc, AccountBook accountBook, String date) {
        System.out.print("품목 : ");
        String item = sc.nextLine();

        System.out.print("금액 : ");
        int price = inputNumber(sc);

        if (price == -1) {
            System.out.println("금액 오류");
            return false;
        }

        if (accountBook.addAccount(date, new Item(item, price))) {
            System.out.println("추가 성공");
        } else {
            System.out.println("추가 실패");
        }

        System.out.print("계속 추가할까요 ? (y/n) ");
        return sc.nextLine().toLowerCase().startsWith("y");
    }

    private static void showAccount(Scanner sc, AccountBook accountBook) {
        String[] dates = accountBook.showDate();
        if(dates.length == 0) {
            System.out.println("항목이 없습니다");
            return;
        }
        int idx = inputIndex(sc, dates.length);
        if (idx == -1) {
            System.out.println("입력 오류");
            return;
        }

        accountBook.showAccount(dates, idx);
    }

    private static void deleteAccount(Scanner sc, AccountBook accountBook) {
        String[] dates = accountBook.showDate();

        int dateIdx = inputIndex(sc, dates.length);
        if (dateIdx == -1) {
            System.out.println("입력 오류");
            return;
        }
        System.out.println("1. 파일 삭제, 2. 데이터 삭제");
        if(Integer.parseInt(sc.nextLine()) == 1) {
            if(accountBook.deleteFile(dates, dateIdx))
                System.out.println("삭제 성공");
            else
                System.out.println("삭제 실패");
            return;
        }


        String[] accounts = accountBook.showAccount(dates, dateIdx);
        if(accounts.length == 0) {
            System.out.println("삭제할 항목이 없습니다.");
            return;
        }

        System.out.print("삭제 할 번호를 선택 ");
        int accountIdx = inputIndex(sc, accounts.length);
        if (accountIdx == -1) {
            System.out.println("입력 오류");
            return;
        }

        if(accountBook.deleteAccount(dates, dateIdx, accounts, accountIdx))
            System.out.println("삭제 성공");
        else
            System.out.println("삭제 실패");

    }

    private static int inputIndex(Scanner sc, int length) {
        System.out.print("인덱스 선택 ");
        int idx = inputNumber(sc);

        if (idx <= 0 || idx > length) {
            return -1;
        }

        return idx;
    }

    private static int inputNumber(Scanner sc) {
        String tmp = sc.nextLine();

        if (!tmp.chars().allMatch(Character::isDigit)) {
            return -1;
        }

        return Integer.parseInt(tmp);
    }
}