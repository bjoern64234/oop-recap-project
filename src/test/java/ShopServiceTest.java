import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;
    private ProductRepo productRepo;
    private OrderListRepo orderListRepo;
    private Product p1, p2, p3, p4;
    private Instant minusOneDay;

    @BeforeEach
    void setUp() {
        // Given
        productRepo = new ProductRepo();
        orderListRepo = new OrderListRepo();
        shopService = new ShopService(productRepo, orderListRepo);
        shopService.addItemToStock("pencil", 3.95);
        shopService.addItemToStock("ruler", 5.95);
        shopService.addItemToStock("paper", 0.65);
        shopService.addItemToStock("filler", 12.50);

        p1 = shopService.getProductByProductName("pencil").orElseThrow(() -> new ProductNotFoundException("pencil"));
        shopService.order(p1.uuid(), 6);
        p2 = shopService.getProductByProductName("paper").orElseThrow(() -> new ProductNotFoundException("paper"));
        shopService.order(p2.uuid(), 300);
        p3 = shopService.getProductByProductName("filler").orElseThrow(() -> new ProductNotFoundException("filler"));
        shopService.order(p3.uuid(), 3);
        p4 = shopService.getProductByProductName("ruler").orElseThrow(() -> new ProductNotFoundException("ruler"));
        shopService.order(p4.uuid(), 3);

        minusOneDay = Instant.now().minus(1, ChronoUnit.DAYS);
    }

    @Test
    void getOrdersByStatus_containsOrdersAfterChangeStatus() {
        // When
        shopService.update("paper", 200, OrderStatus.IN_DELIVERY);
        shopService.update("pencil", 6, OrderStatus.COMPLETED);
        // Then
        Order o3 = shopService.getOrderByProductId("filler");
        Order o4 = shopService.getOrderByProductId("ruler");

        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.PROCESSING);
        assertThat(actual).containsExactlyInAnyOrder(o3, o4);
    }

    @Test
    void getOrdersByStatus_isEmptyByStatusCompleted() {
        // When
        List<Order> actual = shopService.getOrdersByStatus(OrderStatus.COMPLETED);
        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    void order_throwsIllegalArgumentException() {
        // When & Then
        assertThatThrownBy(() -> shopService.order(p1.name(), 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void order_addOrderToList() {
        // When
        Order actual = shopService.getOrderByProductId(p1.name());
        // Then
        assertThat(this.orderListRepo.getAll()).contains(actual);

    }

    @Test
    void order_throwsProductNotFoundException() {
        // When & Then
        assertThatThrownBy(() -> shopService.order("wrongId", 5))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void getOldestOrderPerStatus_matchesByChangeCreatedAt() {
        // Given
        Order expected = this.orderListRepo.getAll().getFirst().withCreatedAt(this.minusOneDay);
        this.orderListRepo.add(expected);
        // When
        Map<String, Order> actual = this.shopService.getOldestOrderPerStatus();
        // Then
        assertThat(actual).containsValue(expected);
        assertThat(actual.get(expected.uuid())).isEqualTo(expected);
    }
}