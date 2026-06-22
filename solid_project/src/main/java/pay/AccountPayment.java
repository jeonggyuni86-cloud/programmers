package pay;

public class AccountPayment implements Payment {
    @Override
    public void pay(String number) {
        System.out.println("[account] : "+ number + "결제 되었습니다");
    }
}
