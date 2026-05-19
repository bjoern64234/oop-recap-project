public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super("The product with the key " + message + " is not found!");
    }
}
