package game.object;

import java.awt.*;

public class Bullet extends GameObject {
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;

        width = 5;
        height = 10;
        speed = 10;
    }

    @Override
    public void update() {
        y -= speed;

        if(y < -height) {
            alive = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}
