package game;

import game.frame.MainFrame;
import game.frame.MenuFrame;
import game.manager.CollisionManager;
import game.manager.GameManager;
import game.object.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("파일럿 이름 : ");
        String name = sc.nextLine();

        Player player = new Player(name);

        MenuFrame menuFrame = new MenuFrame(player);
        menuFrame.init();

        int menu = menuFrame.printMenu();

        if(menu == 1) {
            new MainFrame(
                    new GameManager(
                            player,
                            new CollisionManager()
                    )
            );
        }
    }
}