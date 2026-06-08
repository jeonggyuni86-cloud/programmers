public record Member(Grade grade, String name, String email, String phone) {
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Member member)) return false;

        return this.name.equals(member.name)
                && this.email.equals(member.email)
                && this.phone.equals(member.phone);
    }
}
