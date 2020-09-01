package app.core.exception;

/**
 * @author Karol Bąk
 */
public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
