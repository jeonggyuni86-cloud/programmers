import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Membership membership = new MembershipController();

    }

    private static int printPlan(String[] plans, Scanner sc) {
        for(String plan : plans)
            System.out.println(plan);
        System.out.println("가입 하실 항목을 선택해 주세요");
        return Integer.parseInt(sc.nextLine());
    }

}
