public enum Grade {
    Lite(1, 10),
    Basic(2, 20),
    Premium(3, 30);

    final int idx, LIMIT;
    Grade(int idx, int LIMIT) {
        this.idx = idx;
        this.LIMIT = LIMIT;
    }

    public static Grade fromIdx(int idx) {
        for(Grade grade : values()) if(idx == grade.idx) return grade;
        return null;
    }

    public static Grade getGradeLimit(String grade) {
        return switch(grade.toLowerCase()) {
            case "lite" -> Lite;
            case "basic" -> Basic;
            case "premium" -> Premium;
            default -> throw new IllegalStateException("Unexpected value: " + grade.toLowerCase());
        };
    }
}
