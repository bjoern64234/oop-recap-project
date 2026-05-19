import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void add_isEqualsByAddGivenProduct() {
        // Given
        ProductRepo productRepo = new ProductRepo();
        // When
        String expected = UUID.randomUUID().toString();
        Product newProduct = new Product(expected, "pencil", 3.95);
        productRepo.add(newProduct);
        // Then
        String actual = productRepo.getProducts().getFirst().uuid();
        assertEquals(expected, actual);
    }

    @Test
    void add_isNotEqualsByDifferentProduct() {
        // Given
        ProductRepo productRepo = new ProductRepo();
        Product newProduct = new Product(UUID.randomUUID().toString(), "pencil", 3.95);
        productRepo.add(newProduct);
        // When
        Product expected = new Product(UUID.randomUUID().toString(),"pencil", 3.95);
        // Then
        Product actual = productRepo.getProducts().getFirst();
        assertNotEquals(expected, actual);
    }

    @Test
    void remove_isTrueWhenAddedProductWasRemoved() {
        // Given
        ProductRepo productRepo = new ProductRepo();
        Product newProduct = new Product(UUID.randomUUID().toString(), "pencil", 3.95);
        productRepo.add(newProduct);
        // When
        productRepo.remove(newProduct);
        // Then
        assertTrue(productRepo.getProducts().isEmpty());
    }

    @Test
    void remove_isFalseWhenAddedProductWasNotRemoved() {
        // Given
        ProductRepo productRepo = new ProductRepo();
        Product newProduct = new Product(UUID.randomUUID().toString(), "pencil", 3.95);
        productRepo.add(newProduct);
        // When
        Product wrongProduct = new Product(UUID.randomUUID().toString(), "pencil", 3.95);
        // Then
        productRepo.remove(wrongProduct);
        assertFalse(productRepo.getProducts().isEmpty());
    }

    @Test
    void getById_isEqualByAddedProductKey() {
        // Given
        ProductRepo productRepo = new ProductRepo();
        Product newProduct = new Product(UUID.randomUUID().toString(), "pencil", 3.95);
        productRepo.add(newProduct);
        // When
        Product expected = productRepo.getProducts().getFirst();
        // Then
        Product actual = productRepo.getById(expected.uuid()).orElseThrow(() -> new ProductNotFoundException(expected.uuid()));
        assertEquals(expected, actual);
    }

    @Test
    void getById_isNotEqualByWrongProductKey() {
        // Given
        ProductRepo productRepo = new ProductRepo();
        Product expected = new Product(UUID.randomUUID().toString(), "pencil", 3.95);
        productRepo.add(expected);
        // When
        String uuid = UUID.randomUUID().toString();
        // Then
        assertThatThrownBy(() -> productRepo.getById(uuid)
                .orElseThrow(() -> new ProductNotFoundException(uuid)))
                .isInstanceOf(ProductNotFoundException.class);
    }
}