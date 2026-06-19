public enum Grade {
    VIP(10),
    NORMAL(30);

    private final int LIMIT;
    Grade(int LIMIT) {
        this.LIMIT = LIMIT;
    }
    public int getLimit() {
        return LIMIT;
    }
}
