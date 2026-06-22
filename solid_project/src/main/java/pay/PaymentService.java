package pay;

public class PaymentService {
    private final Payment payment;

    public PaymentService(Payment payment) {
        this.payment = payment;
    }

    public void pay(String number) {
        payment.pay(number);
    }
}
