package app.core.entity.projection;

import app.core.entity.Product;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {Product.class}
)
public interface ProductProjection {

    Long getId();

    String getName();

    BigDecimal getPrice();



}
