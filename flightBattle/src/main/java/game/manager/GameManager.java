package game.manager;

import game.object.Bullet;
import game.object.Enemy;
import game.object.Player;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class GameManager {
    private final Player player;
    private final List<Enemy> enemies;
    private final List<Bullet> bullets;
    private final Queue<Enemy> spawnQueue;
    private final SpawnThread spawnThread;
    private final CollisionManager collisionManager;

    private boolean isGameOver = false;
    private int score = 0;

    private long lastFireTime = 0;
    private static final long FIRE_COOL_TIME = 100; // 연타시 0.1초마다 나감

    public GameManager(Player player, CollisionManager collisionManager) {
        this.player = player;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.collisionManager = collisionManager;

        this.spawnQueue = new ArrayDeque<>();
        this.spawnThread = new SpawnThread(spawnQueue);
        this.spawnThread.start();
    }

    public void update() {
        if(isGameOver) return;

        player.update();
        bullets.forEach(Bullet::update);
        enemies.forEach(Enemy::update);
        addSpawnedEnemies();

        collisionManager.check(player, enemies, bullets);

        removeDeadObjects();

        if(!player.isAlive()) {
            isGameOver = true;
            shutdown();
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    private void addSpawnedEnemies() {
        synchronized (spawnQueue) {
            while (!spawnQueue.isEmpty()) {
                enemies.add(spawnQueue.poll());
            }
        }
    }

    private void removeDeadObjects() {
        enemies.removeIf(enemy -> !enemy.isAlive());
        bullets.removeIf(bullet -> !bullet.isAlive());
    }

    public void draw(Graphics g) {
        player.draw(g);
        enemies.forEach(e -> e.draw(g));
        bullets.forEach(b -> b.draw(g));

        if (isGameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 130, 350);
        }
    }

    public void shutdown() {
        spawnThread.shutdown();
    }

    public void movePlayerLeft() {
        player.moveLeft();
    }

    public void movePlayerRight() {
        player.moveRight();
    }

    public void movePlayerUp() {
        player.moveUp();
    }

    public void movePlayerDown() {
        player.moveDown();
    }

    public void stopPlayerX() {
        player.stopX();
    }

    public void stopPlayerY() {
        player.stopY();
    }

    public void fire() {
        long now = System.currentTimeMillis();

        if (now - lastFireTime < FIRE_COOL_TIME) {
            return;
        }

        bullets.add(new Bullet(
                player.getCenterX(),
                player.getTopY()
        ));

        lastFireTime = now;
    }
}