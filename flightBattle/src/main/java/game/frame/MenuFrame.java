package game.frame;

import game.object.Player;

import java.util.Arrays;
import java.util.Scanner;

public class MenuFrame {
    private final Player player;
    private final Scanner sc;
    private static final int REPEAT = 60;
    private static final int SLEEPTIME = 80;

    public MenuFrame(Player player) {
        this.player = player;
        this.sc = new Scanner(System.in);
    }
    public void init() {

        /*
        introMenu();
        printLoading();
        introStory();
         */
    }

    private void introMenu() {
        System.out.println(" ___       __   ________  ________  ___       ________          ___       __   ________  ________          ________");
        System.out.println("|\\  \\     |\\  \\|\\   __  \\|\\   __  \\|\\  \\     |\\   ___ \\        |\\  \\     |\\  \\|\\   __  \\|\\   __  \\        |\\_____  \\    ");
        System.out.println("\\ \\  \\    \\ \\  \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\    \\ \\  \\_|\\ \\       \\ \\  \\    \\ \\  \\ \\  \\|\\  \\ \\  \\|\\  \\       \\|____|\\ /_   ");
        System.out.println(" \\ \\  \\  __\\ \\  \\ \\  \\\\\\  \\ \\   _  _\\ \\  \\    \\ \\  \\ \\\\ \\       \\ \\  \\  __\\ \\  \\ \\   __  \\ \\   _  _\\            \\|\\  \\ ");
        System.out.println("  \\ \\  \\|\\__\\_\\  \\ \\  \\\\\\  \\ \\  \\\\  \\\\ \\  \\____\\ \\  \\_\\\\ \\       \\ \\  \\|\\__\\_\\  \\ \\  \\ \\  \\ \\  \\\\  \\|          __\\_\\  \\ ");
        System.out.println("   \\ \\____________\\ \\_______\\ \\__\\\\ _\\\\ \\_______\\ \\_______\\       \\ \\____________\\ \\__\\ \\__\\ \\__\\\\ _\\         |\\_______\\");
        System.out.println("    \\|____________|\\|_______|\\|__|\\|__|\\|_______|\\|_______|        \\|____________|\\|__|\\|__|\\|__|\\|__|        \\|_______|");
    }

    private void introStory() {
        System.out.println("===============================================");
        System.out.println(" ######  ########  #######  ########  ##    ## ");
        System.out.println("##    ##    ##    ##     ## ##     ##  ##  ##  ");
        System.out.println("##          ##    ##     ## ##     ##   ####   ");
        System.out.println(" ######     ##    ##     ## ########     ##    ");
        System.out.println("      ##    ##    ##     ## ##   ##      ##    ");
        System.out.println("##    ##    ##    ##     ## ##    ##     ##    ");
        System.out.println(" ######     ##     #######  ##     ##    ##    ");
        System.out.println("===============================================");
        printOneByOne(
                "2022년 러시아 우크라이나 침공은 2023년 마무리 되지만 중국은 대만침공을 실행에 옮기게 된다.\n\n"
                        + "중국은 효율적으로 대만을 공략하기 위해 주변 민주진영 국가들을 먼저 무력화 시킨 뒤 대만을 침공하기로 계획하고,\n\n"
                        + "북한과 함께 가장 인접 국가 한국과 일본을 동시에 먼저 침공하게 된다.\n\n"
                        + "한국은 계엄령을 선포하고 침략하는 중공군과 북한군의 맞서게 된다.\n\n"
                        + "이제 자유민주주의 진영의 평화와 한국의 평화는 "+ player.getName() +"에게 달렸다. 꼭 자유를 수호할 수 있도록!\n\n"
        );
    }

    public int printMenu() {
        System.out.println("[World War 3]");
        System.out.println("[1] 전투");
        System.out.println("[2] 종료");
        return sc.nextInt();
    }


    private void printOneByOne(String story) {
        for(int i = 0 ; i < story.length(); i++) {
            System.out.print(story.charAt(i));
            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void printLoading() {
        final char[] box = new char[50];
        Arrays.fill(box, '□');

        for(int i = 0; i < 50; i++) {
            box[i] = '■';
            System.out.println("\n".repeat(REPEAT));
            System.out.print("┌");
            System.out.print("─".repeat(REPEAT));
            System.out.println("┐");

            System.out.println("\n                 World War 3에 오신걸 환영합니다.\n");
            System.out.print("       ");
            System.out.println(" --------------- Loading . . . . --------------- ");

            System.out.print("       ");
            for(char c : box) System.out.print(c);
            System.out.println();

            System.out.println();
            System.out.print("└");
            System.out.print("─".repeat(REPEAT));
            System.out.print("┘");


            try {
                Thread.sleep(SLEEPTIME);
                System.out.println();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("\n".repeat(REPEAT));
    }

    static void main(String[] args) {
        new MenuFrame(new Player("abc")).init();
    }
}
