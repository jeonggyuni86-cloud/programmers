public class Main {
    static void main(String[] args) {
        MemberManager memberManager = new MemberManager();

    }

    static void printMenu(MemberManager memberManager) {
        System.out.println("=".repeat(30));
        System.out.println(Grade.VIP.name() + " " + memberManager.countVIP() / Grade.VIP.getLimit());
        System.out.println(Grade.NORMAL.name() + " " + memberManager.countNormal() / Grade.NORMAL.getLimit());
        System.out.println("1. 가입");
    }
}
