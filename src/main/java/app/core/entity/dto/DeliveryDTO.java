package app.core.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Data
@NoArgsConstructor
public class DeliveryDTO {

    private Long id;

    private String type;

    private BigDecimal cost;

    public DeliveryDTO(String type, BigDecimal cost) {
        this.type = type;
        this.cost = cost;
    }
}
