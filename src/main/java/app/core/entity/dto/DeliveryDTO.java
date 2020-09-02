package app.core.entity.dto;

import app.core.entity.DeliveryAddress;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Data
public class DeliveryDTO {

    private String type;

    private BigDecimal cost;

    private DeliveryAddress deliveryAddress;
}
