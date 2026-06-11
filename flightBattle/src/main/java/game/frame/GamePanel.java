package game.frame;

import game.config.GameConfig;
import game.manager.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final GameManager gameManager;
    private final Timer timer;

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;
    private boolean spacePressed;

    public GamePanel(GameManager gameManager) {
        this.gameManager = gameManager;

        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this);
        timer.start();

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleInput();

        gameManager.update();
        repaint();
    }

    private void handleInput() {
        if (leftPressed && !rightPressed) {
            gameManager.movePlayerLeft();
        } else if (rightPressed && !leftPressed) {
            gameManager.movePlayerRight();
        } else {
            gameManager.stopPlayerX();
        }

        if (upPressed && !downPressed) {
            gameManager.movePlayerUp();
        } else if (downPressed && !upPressed) {
            gameManager.movePlayerDown();
        } else {
            gameManager.stopPlayerY();
        }

        if (spacePressed) {
            gameManager.fire();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameManager.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}