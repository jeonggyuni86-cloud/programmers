public enum Grade {
    VIP(10),
    NORMAL(30);

    final int LIMIT;
    Grade(int LIMIT) {
        this.LIMIT = LIMIT;
    }
}
