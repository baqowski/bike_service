package app.core.exception.payment;

/**
 * @author Karol Bąk
 */
public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
