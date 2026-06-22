package order;

import pay.PaymentService;

public class OrderService {
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void order(Order order, String paymentNumber) {
        System.out.println(order.getChannel() + "에서 주문 접수");
        paymentService.pay(paymentNumber);
        System.out.printf("%d번 상품 %d개 주문 완료\n", order.getProductId(), order.getAmount());
    }
}
