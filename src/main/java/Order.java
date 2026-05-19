import lombok.With;

import java.time.Instant;

@With
public record Order(String uuid, String productId, int amount, OrderStatus status, Instant updatedAt, Instant createdAt) {
}