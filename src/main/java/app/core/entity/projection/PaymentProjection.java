package app.core.entity.projection;

import app.core.entity.Payment;
import app.core.entity.type.PaymentStatus;
import app.core.entity.type.PaymentType;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {Payment.class}
)
public interface PaymentProjection {

    Long getId();

    String getPayuOrderId();

    PaymentType getType();

    PaymentStatus getStatus();

    OrderProjection getOrder();

}
