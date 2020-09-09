package app.core.entity.projection;

import app.core.entity.Role;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author Karol Bąk
 */
@Projection(
        name = "full",
        types = {Role.class}
)
public interface RoleProjection {

    String getName();

}
