package app.core.entity.dto;

import app.core.entity.type.PaymentStatus;
import app.core.entity.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karol Bąk
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private Long orderId;

    private Long id;

    private String payuOrderId;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

}
