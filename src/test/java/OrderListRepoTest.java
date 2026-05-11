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
        Order newOrder = new Order(expected, UUID.randomUUID().toString(), 12);
        orderListRepo.add(newOrder);
        // Then
        String actual = orderListRepo.getAll().getFirst().uuid();
        assertEquals(expected, actual);
    }

    @Test
    void add_isNotEqualsByDifferentOrder() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderListRepo.add(newOrder);
        // When
        Order expected = new Order(UUID.randomUUID().toString(), "productId", 12);
        // Then
        Order actual = orderListRepo.getAll().getFirst();
        assertNotEquals(expected, actual);
    }

    @Test
    void remove_isTrueWhenAddedOrderWasRemoved() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
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
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderListRepo.add(newOrder);
        // When
        Order anotherOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderListRepo.remove(anotherOrder);
        // Then
        assertFalse(orderListRepo.getAll().isEmpty());
    }

    @Test
    void getById_isEqualByAddedOrderKey() {
        // Given
        OrderListRepo orderListRepo = new OrderListRepo();
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12);
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
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12);
        orderListRepo.add(expected);
        // Then
        Order actual = orderListRepo.getById(UUID.randomUUID().toString());
        assertNotEquals(expected, actual);
    }
}