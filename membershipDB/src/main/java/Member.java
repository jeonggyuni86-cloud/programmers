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

    private static String padRight(String s, int width) {
        int len = 0;
        for (char c : s.toCharArray()) {
            len += c > 127 ? 2 : 1;
        }

        return s + " ".repeat(Math.max(0, width - len));
    }

    @Override
    public String toString() {
        return padRight(String.valueOf(id), 5)
                + padRight(String.valueOf(grade), 10)
                + padRight(name, 15)
                + padRight(email, 30)
                + padRight(phone, 20);
    }
}