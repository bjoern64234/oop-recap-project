import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    private String uuid;
    private Instant createdAt, updatedAt;
    private OrderMapRepo orderMapRepo;

    @BeforeEach
    void setUp() {
        // Given
        uuid = UUID.randomUUID().toString();
        orderMapRepo = new OrderMapRepo();
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @Test
    void add_isEqualsByAddGivenOrder() {
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(expected);
        // Then
        Order actual = orderMapRepo.getAll().getFirst();
        assertEquals(expected, actual);
    }

    @Test
    void add_isNotEqualsByDifferentOrder() {
        // Given
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(newOrder);
        // When
        Order expected = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        // Then
        Order actual = orderMapRepo.getAll().getFirst();
        assertNotEquals(expected, actual);
    }

    @Test
    void remove_isTrueWhenAddedOrderWasRemoved() {
        // Given
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(newOrder);
        // When
        orderMapRepo.remove(newOrder);
        // Then
        assertTrue(orderMapRepo.getAll().isEmpty());
    }

    @Test
    void remove_isFalseWhenAddedOrderWasNotRemoved() {
        // Given
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(newOrder);
        // When
        Order anotherOrder = new Order(UUID.randomUUID().toString(), "productId", 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.remove(anotherOrder);
        // Then
        assertFalse(orderMapRepo.getAll().isEmpty());
    }

    @Test
    void getById_isEqualByAddedOrderKey() {
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(expected);
        // Then
        Order actual = orderMapRepo.getById(expected.uuid());
        assertEquals(expected, actual);
    }

    @Test
    void getById_isNotEqualByWrongProductKey() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(expected);
        // Then
        Order actual = orderMapRepo.getById(UUID.randomUUID().toString());
        assertNotEquals(expected, actual);
    }

    @Test
    void update_isEqualWhenAmountIsChanged() {
        // Given
        String productId = this.uuid;
        Order order = new Order(UUID.randomUUID().toString(), productId, 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(order);
        // When
        int expected = 10;
        Order newOrder = orderMapRepo.update(order, 10, OrderStatus.PROCESSING);
        // Then
        int actual = newOrder.amount();
        assertEquals(expected, actual);
    }

    @Test
    void update_isNotEqualWhenAmountIsWrongChanged() {
        // Given
        String productId = this.uuid;
        Order order = new Order(UUID.randomUUID().toString(), productId, 12, OrderStatus.PROCESSING, updatedAt, createdAt);
        orderMapRepo.add(order);
        // When
        int expected = 22;
        Order newOrder = orderMapRepo.update(order, 10, OrderStatus.PROCESSING);
        // Then
        int actual = newOrder.amount();
        assertNotEquals(expected, actual);

    }
}