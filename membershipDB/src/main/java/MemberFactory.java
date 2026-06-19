public class MemberFactory {
    public static Member from(int id, String grade, String name, String email, String phoneNumber) {
        return new Member(id, Grade.valueOf(grade.toUpperCase()), name, email, phoneNumber);
    }
}