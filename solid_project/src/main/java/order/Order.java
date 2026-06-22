package order;

public class Order {
    private final int productId;
    private final int amount;
    private final OrderChannel channel;

    public Order(int productId, int amount, OrderChannel channel) {
        this.productId = productId;
        this.amount = amount;
        this.channel = channel;
    }

    public int getAmount() {
        return amount;
    }

    public OrderChannel getChannel() {
        return channel;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return String.format(
                "productId=%d, amount=%d, channel=%s",
                productId,
                amount,
                channel
        );
    }
}
