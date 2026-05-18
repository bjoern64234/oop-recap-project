import java.util.List;

public interface OrderListInterface {

    public void add(Order order);

    public Order update(Order order, int amount, OrderStatus status);

    public void remove(Order order);

    public Order getById(String uuid);

    public List<Order> getAll();
}
