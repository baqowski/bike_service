package app.core.entity.projection;

import app.core.entity.Product;
import app.core.entity.ProductCategory;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {Product.class}
)
public interface ProductFullProjection {

    Long getId();

    String getName();

    BigDecimal getPrice();

    String getColor();

    String getImageUrl();

    String getProducer();

    ProductCategory getProductCategory();



}
