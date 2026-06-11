package game.manager;

import game.config.GameConfig;
import game.object.Enemy;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnManager {
    private static final long MIN_INTERVAL = 400;
    private static final long MAX_INTERVAL = 1200;

    private long lastSpawnTime;
    private long nextInterval = randomInterval();

    public Enemy spawn() {
        long now = System.currentTimeMillis();

        if (now - lastSpawnTime < nextInterval) {
            return null;
        }

        lastSpawnTime = now;
        nextInterval = randomInterval();

        int x = ThreadLocalRandom.current()
                .nextInt(0, GameConfig.WIDTH - 40);

        return new Enemy(x, -40);
    }

    private long randomInterval() {
        return ThreadLocalRandom.current()
                .nextLong(MIN_INTERVAL, MAX_INTERVAL + 1);
    }
}