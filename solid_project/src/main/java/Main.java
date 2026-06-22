import order.Order;
import order.OrderChannel;
import order.OrderService;
import pay.CardPayment;
import pay.Payment;
import pay.PaymentService;

public class Main {
    static void main(String[] args) {
        Payment payment = new CardPayment();
        var orderService = new OrderService(new PaymentService(payment));

        Order order = new Order(
                        1001,
                        3,
                        OrderChannel.OFFLINE);

        orderService.order(order, "1234-5678");
    }
}