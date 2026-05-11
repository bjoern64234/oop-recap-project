public record Order(String uuid, String productId, int amount) {

    public Order withAmount(int newAmount) {
        if (newAmount < 1) {
            System.out.println("You can not order less then 1");
            return null;
        }

        return new Order(this.uuid, this.productId, newAmount);
    }
}
