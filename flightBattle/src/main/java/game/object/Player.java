package game.object;

import game.enums.JetType;

import java.awt.*;

public class Player extends GameObject{
    private final String name;
    private JetType jetType;

    private int hp;
    private int score;

    public Player(String name) {
        this.name = name;
    }
    public void init() {}

    public String getName() {

        return this.name;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
