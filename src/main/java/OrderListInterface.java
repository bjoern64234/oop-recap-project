import java.util.List;

public interface OrderListInterface {

    public void add(Order order);

    public void remove(Order order);

    public Order getById(String uuid);

    public List<Order> getAll();
}
