package game.manager;

import game.object.Bullet;
import game.object.Enemy;
import game.object.Player;

import java.awt.*;
import java.util.List;

public class GameManager {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private SpawnManager spawnManager;
    private CollisionManager collisionManager;


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
