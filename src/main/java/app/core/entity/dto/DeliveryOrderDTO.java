package app.core.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karol Bąk
 */
@Data
@NoArgsConstructor
public class DeliveryOrderDTO {

    DeliveryDTO delivery;

    DeliveryAddressDTO deliveryAddress;

    public DeliveryOrderDTO(DeliveryDTO delivery, DeliveryAddressDTO deliveryAddress) {
        this.delivery = delivery;
        this.deliveryAddress = deliveryAddress;
    }
}
