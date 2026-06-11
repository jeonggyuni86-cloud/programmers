package game.object;

import game.config.GameConfig;
import game.enums.JetType;

import java.awt.*;

public class Player extends GameObject{
    private static final int START_X = 225;
    private static final int START_Y = 600;
    private static final int SIZE = 50;
    private static final int DEFAULT_LIFE = 3;
    private static final int DEFAULT_SPEED = 5;

    private final String name;
    private JetType jetType;

    private int life, score;
    private int dx, dy;

    public Player(String name) {
        this.name = name;
        init();
    }
    public void init() {
        this.x = START_X;
        this.y = START_Y;
        this.width = SIZE;
        this.height = SIZE;
        this.speed = DEFAULT_SPEED;
        this.life = DEFAULT_LIFE;
        this.score = 0;
        this.dx = 0;
        this.dy = 0;
        this.alive = true;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void update() {
        x += dx;
        y += dy;

        x = Math.max(0, x);
        y = Math.max(0, y);

        x = Math.min(x, GameConfig.WIDTH - width);
        y = Math.min(y, GameConfig.HEIGHT - height);
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);

        g.drawString("Player : " + name, 10, 20);
        g.drawString("Life : " + life, 10, 40);
        g.drawString("Score : " + score, 10, 60);
    }
}
