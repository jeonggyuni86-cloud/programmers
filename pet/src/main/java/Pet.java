public class Pet {
    private final String name;
    private int fullness;
    private int happiness;

    public Pet(String name) {
        this.name = name;
        this.fullness = 50;
        this.happiness = 50;
    }

    public String getStatus() {
        return "[" + name + "] 포만감: " + fullness + " / 행복감: " + happiness;
    }

    public String feed() {
        fullness = Math.min(100, fullness + 20);
        happiness = Math.min(100, happiness + 5);

        return "[" + name + "] 에게 먹이를 줬어요! 냠냠";
    }

    public String play() {
        happiness = Math.min(100, happiness + 20);
        fullness = Math.max(0, fullness - 10);

        return "[" + name + "]와(과) 신나게 놀았어요!";
    }
}
