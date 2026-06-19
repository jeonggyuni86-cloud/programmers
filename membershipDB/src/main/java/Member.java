public record Member(Grade grade, String name, String email, String phone) {
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Member member)) return false;
        return this.email.equals(member.email);
    }
}