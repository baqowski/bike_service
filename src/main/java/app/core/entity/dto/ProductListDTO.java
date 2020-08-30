package app.core.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Karol Bąk
 */
@Data
public class ProductListDTO {

    private List<ProductDTO> products;
}
