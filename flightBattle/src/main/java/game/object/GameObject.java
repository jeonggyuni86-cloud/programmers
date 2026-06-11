package game.object;

import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected int speed;
    protected boolean alive = true;
    protected final static int DEFAULT_SPEED = 5;

    public abstract void update();
    public abstract void draw(Graphics g);
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public boolean isAlive() {
        return alive;
    }
    public void destroy() {
        alive = false;
    }
}
