import lombok.With;

@With
public record Order(String uuid, String productId, int amount, OrderStatus status) {
}