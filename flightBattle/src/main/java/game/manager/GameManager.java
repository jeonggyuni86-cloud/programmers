package game.manager;

import game.object.Bullet;
import game.object.Enemy;
import game.object.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final Player player;
    private final List<Enemy> enemies;
    private final List<Bullet> bullets;
    private final SpawnManager spawnManager;
    private final CollisionManager collisionManager;
    private long lastFireTime = 0;
    private static final long FIRE_COOL_TIME = 100;

    public GameManager(Player player, SpawnManager spawnManager, CollisionManager collisionManager) {
        this.player = player;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.spawnManager = spawnManager;
        this.collisionManager = collisionManager;
    }


    public void update() {
        player.update();
        bullets.forEach(Bullet::update);
        enemies.forEach(Enemy::update);
        Enemy enemy = spawnManager.spawn();

        if (enemy != null) {
            enemies.add(enemy);
        }

        collisionManager.check(player, enemies, bullets);
        removeDeadObjects();
    }

    private void removeDeadObjects() {
        enemies.removeIf(enemy -> !enemy.isAlive());
        bullets.removeIf(bullet -> !bullet.isAlive());
    }

    public void draw(Graphics g) {
        player.draw(g);
        enemies.forEach(e -> e.draw(g));
        bullets.forEach(e -> e.draw(g));
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
