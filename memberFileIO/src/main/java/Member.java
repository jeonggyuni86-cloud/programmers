public record Member(Grade grade, String name, String email, String phoneNumber){
    public enum Grade {
        VIP,
        NORMAL
    }

    public String toFileString() {
        return grade + "," + name + "," + email + "," + phoneNumber;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s]\t%s\t %s\t %s",
                grade,
                name,
                email,
                phoneNumber
        );
    }
}
