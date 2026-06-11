package game.manager;

import game.object.Bullet;
import game.object.Enemy;
import game.object.Player;

import java.util.List;

public class CollisionManager {

    public void check(Player player, List<Enemy> enemies, List<Bullet> bullets) {
        checkBulletEnemy(enemies, bullets);
        checkPlayerEnemy(player, enemies);
    }

    private void checkBulletEnemy(List<Enemy> enemies, List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.isAlive()
                        && enemy.isAlive()
                        && bullet.getBounds().intersects(enemy.getBounds())) {

                    bullet.destroy();
                    enemy.destroy();
                }
            }
        }
    }

    private void checkPlayerEnemy(Player player, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()
                    && player.isAlive()
                    && player.getBounds().intersects(enemy.getBounds())) {

                enemy.destroy();
                player.hit();
            }
        }
    }
}