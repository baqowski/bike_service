package app.core.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
@AllArgsConstructor
@Builder
public class PayuProductDTO {

    private String name;
    private String unitPrice;
    private String quantity;
}
