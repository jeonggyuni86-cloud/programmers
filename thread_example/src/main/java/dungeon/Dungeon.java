package dungeon;

import java.util.concurrent.Semaphore;

public class Dungeon {
    private final Semaphore semaphore;
    private final int capacity;

    public Dungeon(int capacity) {
        this.capacity = capacity;
        semaphore = new Semaphore(capacity);
    }

    public void enter(String name) throws InterruptedException {

    }
}
