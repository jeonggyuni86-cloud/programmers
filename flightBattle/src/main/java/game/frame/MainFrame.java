package game.frame;

import game.manager.GameManager;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(GameManager gameManager) {
        setTitle("World War 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new GamePanel(gameManager));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}