package ocp;

public class VipDiscount implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return price * 80 / 100;
    }
}
