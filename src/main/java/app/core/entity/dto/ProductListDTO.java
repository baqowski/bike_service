package app.core.entity.dto;

import lombok.AllArgsConstructor;
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
public class ProductListDTO {

    List<ProductCardResponse> products;

    BigDecimal total;

}
