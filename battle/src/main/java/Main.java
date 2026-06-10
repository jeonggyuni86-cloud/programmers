public class Main {
    static void main(String[] args) {
        Entity hero = new Entity("Hero", 100, 30);
        Entity[] monsters = new Entity[] {
                new Entity("슬라임"),
                new Entity("고블린", 100,  30),
                new Entity("드래곤", 120, 20)
        };
        while(isAlive(monsters)) {
            for(Entity monster : monsters) {
                if(!monster.isAlive()) continue;
                print(hero.attack(monster));
                print(monster.attack(hero));
                print(hero.getStatus());
                print(monster.getStatus());

                if(!monster.isAlive()) print(monster.getName() + " 처치!");
                if(!hero.isAlive()) {
                    print(monster.getName() + "에게 다운!");
                    print("게임 오버");
                    return;
                }
            }
        }
        System.out.println("클리어 !");
        print(hero.getStatus());
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    private static boolean isAlive(Entity[] entities) {
        for(Entity entity : entities)
            if(entity.isAlive()) return true;
        return false;
    }
}
