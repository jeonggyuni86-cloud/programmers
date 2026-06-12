package game.manager;

import game.config.GameConfig;
import game.object.Enemy;

import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnThread extends Thread {

    private final Queue<Enemy> queue;
    private volatile boolean running = true;

    public SpawnThread(Queue<Enemy> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {

            int x = ThreadLocalRandom.current()
                    .nextInt(0, GameConfig.WIDTH - 40);

            synchronized (queue) {
                queue.add(new Enemy(x, -40));
            }

            try {
                Thread.sleep(
                        ThreadLocalRandom.current()
                                .nextLong(400, 1201)
                );
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void shutdown() {
        running = false;
        interrupt();
    }
}