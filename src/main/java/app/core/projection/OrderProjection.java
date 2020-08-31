package app.core.projection;

import app.core.entity.Order;
import app.core.entity.User;
import app.core.entity.type.OrderStatus;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {Order.class}
)
public interface OrderProjection {

    Long getId();

    OrderStatus getOrderStatus();

    BigDecimal getAmount();

    User getUser();

    /*UserProjection getUser();*/
}
