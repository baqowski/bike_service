package app.core.entity.projection;

import app.core.entity.Role;
import app.core.entity.User;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "role",
        types = {User.class}
)
public interface UserRoleProjection {

    Role getRole();
}
