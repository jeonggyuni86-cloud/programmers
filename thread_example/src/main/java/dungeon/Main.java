package dungeon;

public class Main {
    static void main(String[] args) {
        Dungeon dungeon = new Dungeon(3);
        String[] names = { "전사", "마법사", "궁수", "도적", "성기사" };

        for(String name : names) {
            new Thread(new Adventurer(dungeon, name)).start();
        }
    }
}
