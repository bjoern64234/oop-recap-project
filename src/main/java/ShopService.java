import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ShopService {

    private final ProductRepo productRepo;
    private final OrderListRepo orderListRepo;

    public ShopService(ProductRepo productRepo, OrderListRepo orderListRepo) {
        this.productRepo = productRepo;
        this.orderListRepo = orderListRepo;
    }

    public void addItemToStock(String name, double price) {
        Product product = new Product(this.generateId(), name, price);
        this.productRepo.add(product);
    }

    public List<Product> sales() {
        return this.productRepo.getProducts();
    }

    public Optional<Product> getProductByProductName(String name) {

        for (Product product : this.sales()) {
            if (name.equals(product.name())) {
                return Optional.of(product);
            }
        }

        return Optional.empty();
    }

    public void order(String productId, int amount) throws ProductNotFoundException, IllegalArgumentException {
        if (amount < 1) {
            throw new IllegalArgumentException("You must at least order one product");
        }

        Product product = this.productRepo.getById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Order order = new Order(this.generateId(), product.name(), amount, OrderStatus.PROCESSING, Instant.now(), Instant.now());
        this.orderListRepo.add(order);
    }

    public void update(String name, int amount, OrderStatus status) {
        Order order = this.getOrderByProductName(name);

        this.orderListRepo.update(order, amount, status);
    }

    public void remove(String name) {
        Order order = this.getOrderByProductName(name);

        this.orderListRepo.remove(order);
    }

    public Order getOrderByProductName(String name) {
        Product product = this.getProductByProductName(name).orElseThrow(() -> new ProductNotFoundException(name));

        return this.getOrderByProductId(product.name());
    }

    public double getPriceOfOrderByProductId(String productId) {
        Order order = this.getOrderByProductId(productId);
        Product product = this.getProductByProductName(productId).orElseThrow(() -> new ProductNotFoundException(productId));

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

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return this.orderListRepo.getAll().stream().filter(order -> order.status() == status).toList();
    }

    public Order getOrderByProductId(String name) {
        List<Order> orders = this.orderListRepo.getAll();

        for (Order order : orders) {
            if (name.equals(order.productId())) {
                return order;
            }
        }

        System.out.println("There is not order for this product");
        return null;
    }

    public Map<String, Order> getOldestOrderPerStatus() {
        return Arrays.stream(OrderStatus.values())
                .parallel()
                .flatMap(status -> this.getOrdersByStatus(status).stream()
                        .min(Comparator.comparing(Order::createdAt))
                        .map(order -> Map.entry(order.uuid(), order))
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String generateId() {
        return UUID.randomUUID().toString();
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
