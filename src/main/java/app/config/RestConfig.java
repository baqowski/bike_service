package app.config;

import app.core.entity.Order;
import app.core.entity.Product;
import app.core.entity.User;
import app.core.projection.OrderProductProjection;
import app.core.projection.OrderProjection;
import app.core.projection.ProductProjection;
import app.core.projection.UserProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * @author Karol Bąk
 */
@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(
                Product.class,
                User.class,
                Order.class
        );

        config.getProjectionConfiguration()
                .addProjection(UserProjection.class)
                .addProjection(OrderProjection.class)
                .addProjection(ProductProjection.class)
                .addProjection(OrderProductProjection.class);
    }
}
