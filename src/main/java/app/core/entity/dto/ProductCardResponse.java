package app.core.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCardResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer count;

}
