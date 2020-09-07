package app.core.entity.projection;

import app.core.entity.Role;
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

    Role getRole();

    /*List<Order> getOrders();*/
}
