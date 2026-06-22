package ocp;

public class GoldDiscount implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return price * 90 / 100;
    }
}
