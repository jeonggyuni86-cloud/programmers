public class MemberFactory {
    public static Member from(String grade, String name, String email, String phoneNumber) {
        return new Member(Member.Grade.valueOf(grade), name, email, phoneNumber);
    }
}
