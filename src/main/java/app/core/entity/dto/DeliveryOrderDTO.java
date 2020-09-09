package app.core.entity.dto;

import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
public class DeliveryOrderDTO {

    DeliveryDTO delivery;

    DeliveryAddressDTO deliveryAddress;
}
