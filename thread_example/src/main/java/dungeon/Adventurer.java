package dungeon;

public class Adventurer implements Runnable {
    private final Dungeon dungeon;
    private final String name;

    public Adventurer(Dungeon dungeon, String name) {
        this.dungeon = dungeon;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            dungeon.enter(name);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
