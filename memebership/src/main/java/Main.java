import java.util.Scanner;

public class Main {
    private static final int EXIT = 7;
    private static final Membership membership = new MembershipController();
    static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        while(true) {
            String[] plans = membership.getPricePlan();
            int grade = printPlan(plans, sc);
            int select = printMenu(grade, sc);

            switch(select) {
                case 1 : addMember(grade, sc); break;
                case 2 : selectEmail(sc); break;
                case 3 : selectName(sc); break;
                case 4 : selectAll(grade); break;
                case 5 : updateMember(sc); break;
                case 6 : deleteMember(sc); break;
                case 7 : return;
                default:
                    System.out.println("잘못 선택하셨습니다.");
                    System.out.println("다시 입력해주세요");
            }
        }
    }

    private static int printPlan(String[] plans, Scanner sc) {
        for(String plan : plans)
            System.out.println(plan);
        System.out.println("가입 하실 항목을 선택해 주세요");
        String next = sc.nextLine();
        return Character.isDigit(next.charAt(0)) ? next.charAt(0) - '0' : 0;
    }

    private static int printMenu(int grade, Scanner sc) {
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " + membership.available(grade) + "]");
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");
        String next = sc.nextLine();
        return Character.isDigit(next.charAt(0)) ? next.charAt(0) - '0' : 0;
    }

    private static void addMember(int grade, Scanner sc) {
        System.out.print("이름을 입력해주세요: ");
        String name = sc.nextLine();
        System.out.print("이메일을 입력해주세요: ");
        String email = sc.nextLine();
        System.out.print("휴대폰 번호를 입력해주세요: ");
        String phone = sc.nextLine();
        if(membership.addMember(grade, new Member(Grade.fromIdx(grade), name, email, phone)))
            System.out.println("추가 성공");
        else
            System.out.println("추가 실패");
    }

    private static  void print(Member[] members) {
        if(members.length == 0) {
            System.out.println("조회 할 내용이 없습니다.");
            return;
        }
        for(Member member : members) {
            System.out.printf("이름 : %s, 이메일 : %s, 휴대폰 : %s\n", member.name(), member.email(), member.phone());
        }
    }

    private static void selectEmail(Scanner sc) {
        System.out.print("조회할 이메일을 입력해주세요: ");
        String email = sc.nextLine();
        print( membership.selectEmail(email));
    }

    private static Member selectEmail(String email) {
        Member[] members = membership.selectEmail(email);
        if(members.length > 0)
            return members[0];
        return null;

    }

    private static void selectName(Scanner sc) {
        System.out.print("조회 할 이름을 입력해 주세요: ");
        String name = sc.nextLine();
        print(membership.selectName(name));
    }

    private static void selectAll(int grade) {
        print(membership.selectAll(grade));
    }

    private static void updateMember(Scanner sc) {
        System.out.print("이메일을 입력해주세요: ");
        String email = sc.nextLine();
        Member before = selectEmail(email);
        if(before == null) {
            System.out.println("사용자를 찾을 수 없습니다.");
            return;
        }
        System.out.print("변경할 이름을 입력해 주세요: ");
        String name = sc.nextLine();

        System.out.print("변경할 휴대폰 번호를 입력해주세요: ");
        String phone = sc.nextLine();

        if(membership.updateMember(before, new Member(before.grade(), name, email, phone)))
            System.out.println("업데이트 성공");
        else System.out.println("업데이트 실패");
    }

    private static void deleteMember(Scanner sc) {
        System.out.print("조회할 이메일을 입력해 주세요: ");
        String email = sc.nextLine();
        Member member = selectEmail(email);
        if(member == null) {
            System.out.println("조회할 내용이 없습니다.");
            return;
        }
        if(membership.deleteMember(member))
            System.out.println("삭제 성공");
        else System.out.println("삭제 실패");
    }
}
