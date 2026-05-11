public class Main {


    static void main() {
        System.out.println("oop-recap-poject");
        System.out.println();

        // Create repos
        ProductRepo productRepo = new ProductRepo();
        OrderListRepo orderListRepo = new OrderListRepo();

        // Create shop service
        ShopService shopService = new ShopService(productRepo, orderListRepo);

        // Add product to product repo
        shopService.addItemToStock("pencil", 3.95);
        shopService.addItemToStock("ruler", 5.95);
        shopService.addItemToStock("paper", 0.65);
        shopService.addItemToStock("filler", 12.50);
        System.out.println("Available products");
        System.out.println(shopService.sales());
        System.out.println();

        // Get product
        Product pencil = shopService.getProductByProductName("pencil");
        shopService.order(pencil.uuid(), 6);
        Product paper = shopService.getProductByProductName("paper");
        shopService.order(paper.uuid(), 300);
        System.out.println();

        // Get all orders
        System.out.println("List of all orders");
        System.out.println(shopService.getAllOrders());
        System.out.println();

        // Remove one order
        System.out.println("Remove order pencil");
        shopService.remove(pencil.uuid());
        System.out.println(shopService.getAllOrders());
        System.out.println();
    }
}