package pay;

public class CardPayment implements Payment {

    @Override
    public void pay(String number) {
        System.out.println("[card] : " + number + "결제 되었습니다.");
    }
}
