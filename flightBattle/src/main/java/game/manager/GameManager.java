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

        spawnManager.update();
        collisionManager.check(player, enemies, bullets);
        
        removeDeadObjects();
    }

    private void removeDeadObjects() {
    }

    public void draw(Graphics g) {
        player.draw(g);
        enemies.forEach(e -> e.draw(g));
        bullets.forEach(e -> e.draw(g));
    }
}
