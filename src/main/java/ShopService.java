import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderListRepo orderListRepo;

    public ShopService(ProductRepo productRepo, OrderListRepo orderListRepo) {
        this.productRepo = productRepo;
        this.orderListRepo = orderListRepo;
    }

    public void addItemToStock(String name, double price) {
        Product product = new Product(UUID.randomUUID().toString(), name, price);
        this.productRepo.add(product);
    }

    public List<Product> sales() {
        return this.productRepo.getProducts();
    }

    public Product getProductByProductName(String name) {

        for (Product product : this.sales()) {
            if (name.equals(product.name())) {
                return product;
            }
        }

        System.out.println("There ist no product in stock with the name " + name);
        return null;
    }

    public void order(String productId, int amount) {
        Product product = this.productRepo.getById(productId);

        if (product == null) {
            System.out.println("A product with the productId " + productId + " is not existing");
            return;
        }

        Order order = new Order(UUID.randomUUID().toString(), productId, amount);
        this.orderListRepo.add(order);
    }

    public void remove(String name) {
        Product product = this.getProductByProductName(name);
        if (product == null) {
            return;
        }

        Order order = this.getOrderByProductId(product.uuid());
        if (order == null) {
            return;
        }

        this.orderListRepo.remove(order);
    }

    public double getPriceOfOrderByProductId(String productId) {
        Order order = this.getOrderByProductId(productId);
        Product product = this.productRepo.getById(productId);

        return order.amount() * product.price();
    }

    public double getTotal() {
        List<Order> orders = this.getAllOrders();
        double total = 0.0;

        for (Order order : orders) {
            total += this.getPriceOfOrderByProductId(order.productId());
        }

        return total;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(this.orderListRepo.getAll());
    }

    public Order getOrderByProductId(String productId) {
        List<Order> orders = this.orderListRepo.getAll();

        for (Order order : orders) {
            if (productId.equals(order.productId())) {
                return order;
            }
        }

        System.out.println("There is not order for this product");
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShopService that = (ShopService) o;
        return Objects.equals(productRepo, that.productRepo) && Objects.equals(orderListRepo, that.orderListRepo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productRepo, orderListRepo);
    }

    @Override
    public String toString() {
        return "ShopService{" +
                "productRepo=" + productRepo +
                ", orderListRepo=" + orderListRepo +
                '}';
    }
}
