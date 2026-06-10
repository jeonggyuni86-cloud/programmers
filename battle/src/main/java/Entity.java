public class Entity {
    private final String name;
    private final int power;
    private int hp;

    public Entity(String name, int hp, int power) {
        this.name = name;
        this.hp = hp;
        this.power = power;
    }

    public Entity(String name) {
        this(name, 30, 5);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public String attack(Entity target) {
        target.takeDamage(this.power);
        return name + "의 공격! " + target.name + "에게 " + this.power + "피해";
    }

    public String getStatus() {
        return name + " : " + hp;
    }

    public String getName() {
        return this.name;
    }
}
