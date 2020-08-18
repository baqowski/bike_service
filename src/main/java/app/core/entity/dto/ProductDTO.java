package app.core.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Data
public class ProductDTO {

    private String name;

    private BigDecimal price;
}
