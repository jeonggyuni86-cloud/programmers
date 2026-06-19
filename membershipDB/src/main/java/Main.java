import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MemberManager memberManager = new MemberManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            int sel = printMenu(memberManager, sc);

            switch (sel) {
                case 1 -> addMember(sc, memberManager);
                case 2 -> showOneMember(sc, memberManager);
                case 3 -> showMembers(memberManager.selectAll());
                case 4 -> updateMember(sc, memberManager);
                case 5 -> deleteMember(sc, memberManager);
                case 6 -> showStatus(memberManager);
                case 7 -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 시도하세요");
            }
        }
    }

    private static int printMenu(MemberManager memberManager, Scanner sc) {
        System.out.println("=".repeat(30));
        System.out.println("1. 회원 가입");
        System.out.println("2. 회원 단건 조회");
        System.out.println("3. 회원 전체 조회");
        System.out.println("4. 회원 수정");
        System.out.println("5. 회원 탈퇴");
        System.out.println("6. VIP / NORMAL 현황");
        System.out.println("7. 종료");
        System.out.print("> ");

        return selectNum(sc.nextLine(), 1, 7);
    }

    private static int selectNum(String num, int start, int end) {
        if (num == null || num.isBlank()) return -1;
        if (!num.chars().allMatch(Character::isDigit)) return -1;

        int n = Integer.parseInt(num);
        return n >= start && n <= end ? n : -1;
    }

    private static void addMember(Scanner sc, MemberManager memberManager) {
        Grade grade = inputGrade(sc);

        System.out.print("이름: ");
        String name = sc.nextLine();

        System.out.print("이메일: ");
        String email = sc.nextLine();

        System.out.print("전화번호: ");
        String phone = sc.nextLine();

        Member member = MemberFactory.from(0, grade.name(), name, email, phone);
        memberManager.addMember(member);
    }

    private static void showOneMember(Scanner sc, MemberManager memberManager) {
        System.out.println("1. 이름으로 조회");
        System.out.println("2. 이메일로 조회");
        System.out.print("> ");

        int sel = selectNum(sc.nextLine(), 1, 2);

        Member member = null;

        switch (sel) {
            case 1 -> {
                System.out.print("이름: ");
                String name = sc.nextLine();
                member = memberManager.selectByName(name);
            }
            case 2 -> {
                System.out.print("이메일: ");
                String email = sc.nextLine();
                member = memberManager.selectByEmail(email);
            }
            default -> {
                System.out.println("잘못된 입력입니다.");
                return;
            }
        }

        if (member == null) {
            System.out.println("해당 회원이 없습니다.");
            return;
        }

        System.out.println(member);
    }
    private static void updateMember(Scanner sc, MemberManager memberManager) {
        System.out.print("수정할 회원 ID: ");
        int id = selectNum(sc.nextLine(), 1, Integer.MAX_VALUE);

        if (id == -1) {
            System.out.println("잘못된 ID입니다.");
            return;
        }

        System.out.print("새 이름: ");
        String name = sc.nextLine();

        System.out.print("새 이메일: ");
        String email = sc.nextLine();

        System.out.print("새 전화번호: ");
        String phone = sc.nextLine();

        memberManager.updateMember(id, name, email, phone);
    }

    private static void deleteMember(Scanner sc, MemberManager memberManager) {
        System.out.print("탈퇴할 회원 ID: ");
        int id = selectNum(sc.nextLine(), 1, Integer.MAX_VALUE);

        if (id == -1) {
            System.out.println("잘못된 ID입니다.");
            return;
        }

        memberManager.deleteMember(id);
    }

    private static Grade inputGrade(Scanner sc) {
        while (true) {
            System.out.print("등급(VIP/NORMAL): ");
            String input = sc.nextLine().trim().toUpperCase();

            try {
                return Grade.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("잘못된 등급입니다. VIP 또는 NORMAL을 입력하세요.");
            }
        }
    }

    private static void showMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }

        for (Member member : members) {
            System.out.println(member);
        }
    }

    private static void showStatus(MemberManager memberManager) {
        System.out.println(
                Grade.VIP.name() + " " +
                        memberManager.countVIP() + " / " +
                        Grade.VIP.getLimit()
        );

        System.out.println(
                Grade.NORMAL.name() + " " +
                        memberManager.countNormal() + " / " +
                        Grade.NORMAL.getLimit()
        );
    }
}