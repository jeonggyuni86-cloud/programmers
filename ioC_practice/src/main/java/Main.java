import bean.CoffeeBean;
import container.CoffeeContainer;
import hollywood.Button;
import hollywood.LikeAction;
import service.CoffeeMaker;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== 2. DI =====");
        new CoffeeMaker(CoffeeBean.COLOMBIA).brew();
        new CoffeeMaker(CoffeeBean.ETHIOPIA).brew();

        System.out.println("\n===== 3. IoC 컨테이너 =====");
        CoffeeMaker maker = new CoffeeContainer().getCoffeeMaker();
        maker.brew();

        System.out.println("\n===== 4. 헐리우드 원칙 =====");
        Button button = new Button(new LikeAction());
        button.press();
    }
}