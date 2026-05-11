import java.util.*;

public class OrderMapRepo implements OrderListInterface{

    private Map<String, Order> order;

    public OrderMapRepo() {
        this.order = new HashMap<>();
    }

    @Override
    public void add(Order order) {
        this.order.put(order.uuid(), order);
    }

    @Override
    public void remove(Order order) {
        this.order.remove(order.uuid());
    }

    @Override
    public Order getById(String uuid) {

        for (Order order : this.order.values()) {
            if (order.uuid().equals(uuid)) {
                return order;
            }
        }

        return null;
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(this.order.values());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderMapRepo that = (OrderMapRepo) o;
        return Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(order);
    }

    @Override
    public String toString() {
        return "OrderMapRepo{" +
                "order=" + order +
                '}';
    }
}
