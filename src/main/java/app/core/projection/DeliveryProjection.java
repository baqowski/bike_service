package app.core.projection;

import app.core.entity.Delivery;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {Delivery.class}
)
public interface DeliveryProjection {

    Long getId();

    String getType();

    BigDecimal getCost();

    /*List<DeliveryOrderProjection> getDeliveryOrders();*/

}
