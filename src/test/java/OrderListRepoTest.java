import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void add_isEqualsByAddGivenOrder() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        String expected = UUID.randomUUID().toString();
        // When
        Order newOrder = new Order(expected, UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING);
        orderListRepo.add(newOrder);
        // Then
        String actual = orderListRepo.getAll().getFirst().uuid();
        assertEquals(expected, actual);
    }

    @Test
    void add_isNotEqualsByDifferentOrder() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING);
        orderListRepo.add(newOrder);
        // When
        Order expected = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING);
        // Then
        Order actual = orderListRepo.getAll().getFirst();
        assertNotEquals(expected, actual);
    }

    @Test
    void remove_isTrueWhenAddedOrderWasRemoved() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING);
        orderListRepo.add(newOrder);
        // When
        orderListRepo.remove(newOrder);
        // Then
        assertTrue(orderListRepo.getAll().isEmpty());
    }

    @Test
    void remove_isFalseWhenAddedOrderWasNotRemoved() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING);
        orderListRepo.add(newOrder);
        // When
        Order anotherOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING);
        orderListRepo.remove(anotherOrder);
        // Then
        assertFalse(orderListRepo.getAll().isEmpty());
    }

    @Test
    void getById_isEqualByAddedOrderKey() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING);
        orderListRepo.add(expected);
        // Then
        Order actual = orderListRepo.getById(expected.uuid());
        assertEquals(expected, actual);
    }

    @Test
    void getById_isNotEqualByWrongProductKey() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING);
        orderListRepo.add(expected);
        // Then
        Order actual = orderListRepo.getById(UUID.randomUUID().toString());
        assertNotEquals(expected, actual);
    }

    @Test
    void update_isEqualWhenAmountIsChanged() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        String productId = UUID.randomUUID().toString();
        Order order = new Order(UUID.randomUUID().toString(), productId, 12, OrderStatus.PROCESSING);
        orderListRepo.add(order);
        // When
        int expected = 10;
        Order newOrder = orderListRepo.update(order, 10);
        // Then
        int actual = newOrder.amount();
        assertEquals(expected, actual);

    }

    @Test
    void update_isNotEqualWhenAmountIsWrongChanged() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        String productId = UUID.randomUUID().toString();
        Order order = new Order(UUID.randomUUID().toString(), productId, 12, OrderStatus.PROCESSING);
        orderListRepo.add(order);
        // When
        int expected = 22;
        Order newOrder = orderListRepo.update(order, 10);
        // Then
        int actual = newOrder.amount();
        assertNotEquals(expected, actual);

    }
}