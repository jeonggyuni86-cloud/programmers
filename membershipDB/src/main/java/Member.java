public record Member(int id, Grade grade, String name, String email, String phone) {
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Member member)) return false;
        return this.email.equals(member.email);
    }
    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%-5d %-8s %-10s %-25s %-15s",
                id,
                grade,
                name,
                email,
                phone
        );
    }
}