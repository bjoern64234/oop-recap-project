import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductRepo {

    private final List<Product> products;

    public ProductRepo() {
        this.products = new ArrayList<>();
    }

    public void add(Product product) {

        this.products.add(product);
        System.out.println("A new Product " + product.name() + " was added to Stock.");
    }

    public void remove(Product product) {
        this.products.remove(product);
        System.out.println("Product with the uuid " + product.name() + " was removed.");
    }

    public Optional<Product> getById(String uuid) {
        return this.products.stream()
                .filter(product -> uuid.equals(product.uuid()))
                .findFirst();
    }

    public List<Product> getProducts() {
        return this.products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductRepo that = (ProductRepo) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }

    @Override
    public String toString() {
        return "ProductRepo{" +
                "products=" + products +
                '}';
    }
}
