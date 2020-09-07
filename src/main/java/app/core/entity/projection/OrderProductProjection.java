package app.core.entity.projection;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.Product;
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

    /*ProductProjection getProduct();*/
    Product getProduct();
    Order getOrder();
}
