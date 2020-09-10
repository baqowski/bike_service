package app.core.entity.dto;

import app.core.entity.type.OrderServiceType;
import app.core.entity.type.OrderStatus;
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

    private OrderStatus orderStatus;

    private OrderServiceType orderServiceType;

    private List<ProductDTO> products;

    /*private DeliveryDTO deliveryOrder;*/
    private DeliveryOrderDTO deliveryOrder;

    private PaymentDTO payment;
}
