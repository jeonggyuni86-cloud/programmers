package game.frame;

import game.config.GameConfig;
import game.manager.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final GameManager gameManager;
    private final Timer timer;

    public GamePanel(GameManager gameManager) {
        this.gameManager = gameManager;
        this.timer = new Timer(16, this);
        setPreferredSize(
                new Dimension(
                        GameConfig.WIDTH,
                        GameConfig.HEIGHT
                )
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameManager.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameManager.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
