import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberManager manager = new MemberManager();
        while(true) {

            System.out.println("1. 등록");
            System.out.println("2. 조회");
            System.out.println("3. 수정");
            System.out.println("4. 삭제");
            System.out.println("0. 종료");

            int menu = Integer.parseInt(sc.nextLine());

            switch(menu) {
                case 1 -> register(sc, manager);
                case 2 -> printMembers(manager);
                case 3 -> update(sc, manager);
                case 4 -> delete(sc, manager);
                case 0 -> {
                    return;
                }
            }
        }
    }
    private static void register(Scanner sc, MemberManager manager) {
        System.out.println("등급: " + Arrays.toString(Member.Grade.values()));

        Member.Grade grade;
        while(true) {
            try {
                System.out.print("등급: ");
                grade = Member.Grade.valueOf(sc.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("다시 입력하세요.");
            }
        }
        System.out.print("이름: ");
        String name = sc.nextLine();

        System.out.print("이메일: ");
        String email = sc.nextLine();

        System.out.print("전화번호: ");
        String phoneNumber = sc.nextLine();
        manager.add(new Member(grade, name, email, phoneNumber));
        System.out.println("등록 완료");

    }
    private static boolean printMembers(MemberManager manager) {
        List<Member> members = manager.findAll();

        if(members.isEmpty()) {
            System.out.println("조회할 회원이 없습니다.");
            return false;
        }

        System.out.printf("NO.\t[%s]\t[%s]\t[%s]\t[%s]\n", "등급", "이름", "이메일", "전화번호");
        for(int i = 0; i < members.size(); i++)
            System.out.println((i + 1) + "\t" + members.get(i));
        return true;
    }

    private static void delete(Scanner sc, MemberManager manager) {
        if(!printMembers(manager)) return;

        System.out.print("삭제 번호: ");
        int idx = Integer.parseInt(sc.nextLine());

        if(manager.delete(idx - 1)) {
            System.out.println("삭제 완료");
        } else {
            System.out.println("삭제 실패");
        }
    }

    private static void update(Scanner sc, MemberManager manager) {
        if(!printMembers(manager)) return;

        System.out.print("수정 번호: ");
        int idx = Integer.parseInt(sc.nextLine());

        System.out.print("등급(VIP/NORMAL): ");
        String grade = sc.nextLine();

        System.out.print("이름: ");
        String name = sc.nextLine();

        System.out.print("이메일: ");
        String email = sc.nextLine();

        System.out.print("전화번호: ");
        String phone = sc.nextLine();

        boolean result = manager.update(idx - 1, MemberFactory.from(grade, name, email, phone));

        System.out.println(
                result ? "수정 완료" : "수정 실패"
        );
    }
}
