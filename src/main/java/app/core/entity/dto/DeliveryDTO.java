package app.core.entity.dto;

import app.core.entity.Delivery;
import app.core.entity.DeliveryAddress;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
public class DeliveryDTO {

    private Delivery delivery;

    private DeliveryAddress deliveryAddress;
}
