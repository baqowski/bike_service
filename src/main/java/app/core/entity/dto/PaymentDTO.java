package app.core.entity.dto;

import app.core.entity.type.PaymentStatus;
import app.core.entity.type.PaymentType;
import lombok.Builder;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
@Builder

public class PaymentDTO {

    private Long orderId;

    private String payuOrderId;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;


}
