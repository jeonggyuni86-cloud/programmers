import dip.EmailSender;
import dip.NotificationService;
import dip.SmsSender;
import lsp.Bird;
import lsp.Penguin;
import lsp.Sparrow;
import ocp.BasicDiscount;
import ocp.DiscountPolicy;
import ocp.GoldDiscount;
import ocp.VipDiscount;

public class Main {
    static void main(String[] args) {
        DiscountPolicy[] policies = { new BasicDiscount(), new GoldDiscount(), new VipDiscount() };
        String[] names = {"일반", "골드", "vip"};
        for (int i = 0; i < policies.length; i++) {
            System.out.println(names[i] + " 회원 -> " + policies[i].discount(10000) + "원");
        }

        // LSP: Bird 타입으로 묶어도 안 터짐
        Bird[] birds = { new Sparrow(), new Penguin() };
        for (Bird b : birds) b.eat();

        // DIP: 구현체를 '주입'으로 교체
        new NotificationService(new EmailSender()).notifyUser("주문이 완료되었습니다");
        new NotificationService(new SmsSender()).notifyUser("주문이 완료되었습니다");
    }
}
