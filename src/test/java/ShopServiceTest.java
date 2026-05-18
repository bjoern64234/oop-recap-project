import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;
    private ProductRepo productRepo;
    private OrderListRepo orderListRepo;
    private Product p1, p2, p3, p4;

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

        p1 = shopService.getProductByProductName("pencil");
        shopService.order(p1.uuid(), 6);
        p2 = shopService.getProductByProductName("paper");
        shopService.order(p2.uuid(), 300);
        p3 = shopService.getProductByProductName("filler");
        shopService.order(p3.uuid(), 3);
        p4 = shopService.getProductByProductName("ruler");
        shopService.order(p4.uuid(), 3);
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
}