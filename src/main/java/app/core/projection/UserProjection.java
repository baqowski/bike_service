package app.core.projection;

import app.core.entity.User;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {User.class}
)
public interface UserProjection {

    Long getId();

    String getUsername();

    /*List<Order> getOrders();*/
}
