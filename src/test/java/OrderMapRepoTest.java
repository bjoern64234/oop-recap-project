import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void add_isEqualsByAddGivenOrder() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12);
        orderMapRepo.add(expected);
        // Then
        Order actual = orderMapRepo.getAll().getFirst();
        assertEquals(expected, actual);
    }

    @Test
    void add_isNotEqualsByDifferentOrder() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderMapRepo.add(newOrder);
        // When
        Order expected = new Order(UUID.randomUUID().toString(), "productId", 12);
        // Then
        Order actual = orderMapRepo.getAll().getFirst();
        assertNotEquals(expected, actual);
    }

    @Test
    void remove_isTrueWhenAddedOrderWasRemoved() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderMapRepo.add(newOrder);
        // When
        orderMapRepo.remove(newOrder);
        // Then
        assertTrue(orderMapRepo.getAll().isEmpty());
    }

    @Test
    void remove_isFalseWhenAddedOrderWasNotRemoved() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        Order newOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderMapRepo.add(newOrder);
        // When
        Order anotherOrder = new Order(UUID.randomUUID().toString(), "productId", 12);
        orderMapRepo.remove(anotherOrder);
        // Then
        assertFalse(orderMapRepo.getAll().isEmpty());
    }

    @Test
    void getById_isEqualByAddedOrderKey() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        // When
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12);
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
        Order expected = new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 12);
        orderMapRepo.add(expected);
        // Then
        Order actual = orderMapRepo.getById(UUID.randomUUID().toString());
        assertNotEquals(expected, actual);
    }

    @Test
    void update_isEqualWhenAmountIsChanged() {
        // Given
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        String productId = UUID.randomUUID().toString();
        Order order = new Order(UUID.randomUUID().toString(), productId, 12);
        orderMapRepo.add(order);
        // When
        int expected = 10;
        Order newOrder = orderMapRepo.update(order, 10);
        // Then
        int actual = newOrder.amount();
        assertEquals(expected, actual);
    }
}