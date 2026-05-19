import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    private String uuid;
    private Instant createdAt, updatedAt;
    private OrderListRepo orderListRepo;

    @BeforeEach
    void setUp() {
        // Given
        uuid = UUID.randomUUID().toString();
        orderListRepo = new OrderListRepo();
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @Test
    void add_isEqualsByAddGivenOrder() {
        // Given
        String expected = this.uuid;
        // When
        Order newOrder = new Order(expected, UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(newOrder);
        // Then
        String actual = orderListRepo.getAll().getFirst().uuid();
        assertEquals(expected, actual);
    }

    @Test
    void add_isNotEqualsByDifferentOrder() {
        // Given
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(newOrder);
        // When
        Order expected = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        // Then
        Order actual = orderListRepo.getAll().getFirst();
        assertNotEquals(expected, actual);
    }

    @Test
    void remove_isTrueWhenAddedOrderWasRemoved() {
        // Given
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(newOrder);
        // When
        orderListRepo.remove(newOrder);
        // Then
        assertTrue(orderListRepo.getAll().isEmpty());
    }

    @Test
    void remove_isFalseWhenAddedOrderWasNotRemoved() {
        // Given
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(newOrder);
        // When
        Order anotherOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.remove(anotherOrder);
        // Then
        assertFalse(orderListRepo.getAll().isEmpty());
    }

    @Test
    void getById_isEqualByAddedOrderKey() {
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(expected);
        // Then
        Order actual = orderListRepo.getById(expected.uuid());
        assertEquals(expected, actual);
    }

    @Test
    void getById_isNotEqualByWrongProductKey() {
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(expected);
        // Then
        Order actual = orderListRepo.getById(UUID.randomUUID().toString());
        assertNotEquals(expected, actual);
    }

    @Test
    void update_isEqualWhenAmountIsChanged() {
        String productId = UUID.randomUUID().toString();
        Order order = new Order(UUID.randomUUID().toString(), productId, 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(order);
        // When
        int expected = 10;
        Order newOrder = orderListRepo.update(order, 10, OrderStatus.PROCESSING);
        // Then
        int actual = newOrder.amount();
        assertEquals(expected, actual);

    }

    @Test
    void update_isNotEqualWhenAmountIsWrongChanged() {
        String productId = UUID.randomUUID().toString();
        Order order = new Order(UUID.randomUUID().toString(), productId, 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderListRepo.add(order);
        // When
        int expected = 22;
        Order newOrder = orderListRepo.update(order, 10, OrderStatus.PROCESSING);
        // Then
        int actual = newOrder.amount();
        assertNotEquals(expected, actual);

    }
}