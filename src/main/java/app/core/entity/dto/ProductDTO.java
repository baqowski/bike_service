package app.core.entity.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */

@Data
@Builder
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer count;

    ProductDTO(Integer count) {
        this.count = count;
    }

    ProductDTO() {

    }
}
