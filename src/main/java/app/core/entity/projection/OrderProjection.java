package app.core.entity.projection;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.type.OrderServiceType;
import app.core.entity.type.OrderStatus;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    OrderServiceType getOrderServiceType();

    BigDecimal getAmount();

    Integer getLoanDays();

    LocalDateTime getLoanStart();

    LocalDateTime getLoanTermination();

    Payment getPayment();

    String getDescription();

    DeliveryOrderProjection getDeliveryOrder();

/*    List<OrderProduct> getProducts();*/

    List<OrderProductProjection> getProducts();

    /*List<OrderProductProjection> getProducts();

    DeliveryOrderProjection getDeliveryOrder();

    PaymentProjection getPayment();

    UserProjection getUser();*/
}
