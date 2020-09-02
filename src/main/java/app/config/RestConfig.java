package app.config;

import app.core.entity.Delivery;
import app.core.entity.Order;
import app.core.entity.Product;
import app.core.entity.User;
import app.core.projection.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

/**
 * @author Karol Bąk
 */
@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(
                Product.class,
                User.class,
                Order.class,
                Delivery.class
        );

        config.getProjectionConfiguration()
                .addProjection(UserProjection.class)
                .addProjection(OrderProjection.class)
                .addProjection(ProductProjection.class)
                .addProjection(OrderProductProjection.class)
                .addProjection(UserRoleProjection.class);
    }
}
