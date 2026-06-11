package game.object;

import java.awt.*;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean alive = true;

    public abstract void update();
    public abstract void draw(Graphics g);
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public boolean isAlive() {
        return alive;
    }
}
