package game.object;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public abstract void move();
}
