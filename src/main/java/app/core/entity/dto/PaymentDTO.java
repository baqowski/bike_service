package app.core.entity.dto;

import app.core.entity.type.PaymentType;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
public class PaymentDTO {

    private Long orderId;

    private PaymentType paymentType;

}
