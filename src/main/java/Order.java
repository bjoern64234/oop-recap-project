public record Order(String uuid, String productId, int amount, OrderStatus status) {

    public Order withAmount(int newAmount) {
        if (newAmount < 1) {
            System.out.println("You cannot order less than 1");
            return this;
        }

        return new Order(this.uuid, this.productId, newAmount, this.status);
    }

    public Order withStatus(OrderStatus status) {
        return new Order(this.uuid, this.productId, this.amount, status);
    }
}