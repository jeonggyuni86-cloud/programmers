import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("반려 동물의 이름을 정해주세요 : ");
        String name = sc.nextLine();
        Pet pet = new Pet(name);
        print(pet.getStatus());

        while(true) {
            print("\n 무엇을 할까요? [1] 먹이주기 [2] 놀아주기 [3] 상태보기 [4] 종료");
            System.out.print("> ");
            int menu = getNum(sc);
            if(menu == -1) continue;
            else if(menu == 4) break;

            switch(menu) {
                case 1 :
                    print(pet.feed());
                    print(pet.getStatus());
                    break;
                case 2 :
                    print(pet.play());
                    print(pet.getStatus());
                    break;
                case 3 :
                    print(pet.getStatus());
                    break;
                default : break;
            }
        }
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    private static int getNum(Scanner sc) {
        String str = sc.nextLine();
        return str.chars().allMatch(Character::isDigit)
                ? Integer.parseInt(str)
                : -1;
    }
}
