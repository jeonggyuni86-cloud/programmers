package game.object;

import game.config.GameConfig;

import java.awt.*;

public class Enemy extends GameObject{
    private static final int SIZE = 40;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = SIZE;
        this.height = SIZE;
        this.speed = DEFAULT_SPEED - 2;
    }

    @Override
    public void update() {
        y += speed;
        if(y > GameConfig.HEIGHT) alive = false;
    }

    @Override
    public void draw(Graphics g) {
        g.drawRect(x, y, width, height);
    }
}
