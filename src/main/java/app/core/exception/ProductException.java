package app.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Karol Bąk
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }
}
