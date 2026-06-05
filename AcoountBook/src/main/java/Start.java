
import java.util.Scanner;

public class Start {
    static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final AccountBookImpl account = new AccountBookImpl();
        String date, name;
        int price;
        Item item;
        while(true) {
            System.out.println("======= 가계부 ======");
            System.out.println("1. 내역 추가");
            System.out.println("2. 내역 조회");
            System.out.println("3. 전체 삭제");
            System.out.println("4. 내역 삭제");
            System.out.println("5. 종료");

            switch(sc.nextInt()) {
                case 1:
                    date = print(sc);
                    System.out.println("항목 이름 > ");
                    name = sc.nextLine();
                    System.out.println("금액 > ");
                    price = sc.nextInt();
                    item = new Item(name, price);
                    account.addAccount(date, item);
                    break;
                case 2:
                    date = print(sc);
                    account.showAccount(date);
                    break;

                case 3:
                    date = print(sc);
                    account.deleteAll(date);
                    break;

                case 4:
                    date = print(sc);
                    account.showAccount(date);
                    System.out.println("삭제할 번호를 선택해주세요 > ");
                    int idx = sc.nextInt();
                    account.deleteItem(date, idx);
                    break;

                case 5 :
                    return;
            }
        }
    }
    private static String print(Scanner sc) {
        sc.nextLine();
        System.out.println("날짜 입력 (xxxx-xx-xx) > ");
        return sc.nextLine();
    }
}
