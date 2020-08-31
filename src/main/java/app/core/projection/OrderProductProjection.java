package app.core.projection;

import app.core.entity.OrderProduct;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {OrderProduct.class}
)
public interface OrderProductProjection {

    Integer getQuantity();
}
