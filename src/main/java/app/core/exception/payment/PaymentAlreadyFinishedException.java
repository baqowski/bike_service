package app.core.exception.payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Karol Bąk
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentAlreadyFinishedException extends PaymentException {
    public PaymentAlreadyFinishedException(String message) {
        super(message);
    }
}
