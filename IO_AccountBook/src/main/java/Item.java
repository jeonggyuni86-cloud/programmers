public record Item(String name, int price) {
    @Override
    public String toString() {
        return name + ":" + price;
    }
}
