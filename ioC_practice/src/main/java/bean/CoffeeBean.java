package bean;

public enum CoffeeBean implements Bean {
    COLOMBIA("콜롬비아 원두"),
    ETHIOPIA("에티오피아 원두");

    private final String origin;
    CoffeeBean(String origin) {
        this.origin = origin;
    }

    @Override
    public String getOrigin() {
        return origin;
    }
}
