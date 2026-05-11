import java.util.*;

public class OrderListRepo implements OrderListInterface {

    private final List<Order> orders;

    public OrderListRepo() {
        this.orders = new ArrayList<>();
    }

    @Override
    public void add(Order order) {
        this.orders.add(order);
    }

    @Override
    public void remove(Order order) {
        this.orders.remove(order);
    }

    @Override
    public Order getById(String uuid) {

        for (Order order : this.orders) {
            if (uuid.equals(order.uuid())) {
                return order;
            }
        }

        return null;
    }

    @Override
    public List<Order> getAll() {
        return this.orders;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderListRepo that = (OrderListRepo) o;
        return Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orders);
    }

    @Override
    public String toString() {
        return "OrderListRepo{" +
                "orders=" + orders +
                '}';
    }
}
