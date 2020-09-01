package app.core.entity.dto;

import app.core.entity.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;

    private BigDecimal amount;

    private PaymentType paymentType;

    private List<ProductDTO> products;
}
