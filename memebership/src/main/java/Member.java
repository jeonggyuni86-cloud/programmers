public class Member {
    String name;
    String email;
    String phone;
    Grade grade;

    Member(Grade grade, String name, String email, String phone) {
        this.grade = grade;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Member member)) return false;

        return this.name.equals(member.name)
                && this.email.equals(member.email)
                && this.phone.equals(member.phone);
    }
}
