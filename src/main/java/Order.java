public record Order(String uuid, String productId, int amount) {

    public void withAmount(int newAmount) {
        if (newAmount < 1) {
            System.out.println("You can not order less then 1");
            return;
        }

        new Order(this.uuid, this.productId, newAmount);
    }
}
