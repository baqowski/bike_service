package app.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Karol Bąk
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
