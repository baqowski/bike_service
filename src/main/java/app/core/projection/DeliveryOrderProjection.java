package app.core.projection;

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

    /*List<OrderProjection> getOrders();*/

    DeliveryProjection getDelivery();

    DeliveryAddressProjection getDeliveryAddress();


}
