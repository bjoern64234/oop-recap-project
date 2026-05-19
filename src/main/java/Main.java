import java.time.Instant;
import java.util.List;

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
        try {
            Product pencil = shopService.getProductByProductName("pencil").orElseThrow(() -> new ProductNotFoundException("pencil"));
            shopService.order(pencil.uuid(), 6);
        } catch (IllegalArgumentException | ProductNotFoundException e) {
            System.err.println("Fehler: " + e.getMessage());
        }

        try {
            Product paper = shopService.getProductByProductName("paper").orElseThrow(() -> new ProductNotFoundException("paper"));
            shopService.order(paper.uuid(), 300);
        } catch (IllegalArgumentException | ProductNotFoundException e) {
            System.err.println("Fehler: " + e.getMessage());
        }

        try {
            Product filler = shopService.getProductByProductName("filler").orElseThrow(() -> new ProductNotFoundException("filler"));
            shopService.order(filler.uuid(), 3);
        } catch (IllegalArgumentException | ProductNotFoundException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        System.out.println();

        // Get all orders
        System.out.println("List of all orders");
        System.out.println(shopService.getAllOrders());
        System.out.println();

        // Remove one order
        System.out.println("Remove order pencil");
        shopService.remove("pencil");
        System.out.println(shopService.getAllOrders());
        System.out.println();

        // Update order for product paper
        System.out.println("Update order for product paper to 200");
        shopService.update("paper", 200, OrderStatus.IN_DELIVERY);
        System.out.println(shopService.getAllOrders());
        System.out.println();

        // Get total amount
        System.out.println(shopService.getTotal());
        System.out.println();

        // Get orders by status
        List<Order> ordersByStatus = shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY);
        System.out.println(ordersByStatus);
    }
}