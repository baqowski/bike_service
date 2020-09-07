package app.core.entity.projection;

import app.core.entity.DeliveryOrder;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {DeliveryOrder.class}
)
public interface DeliveryOrderProjection {

    Long getId();

    DeliveryProjection getDelivery();

    DeliveryAddressProjection getDeliveryAddress();


}
