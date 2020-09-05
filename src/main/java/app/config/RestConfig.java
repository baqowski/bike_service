package app.config;

import app.core.entity.*;
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
                Delivery.class,
                Order.class,
                DeliveryOrder.class,
                OrderProduct.class,
                ProductCategory.class,
                Role.class,
                Payment.class,
                DeliveryAddress.class
        );
        config.getProjectionConfiguration()
                .addProjection(UserProjection.class)
                .addProjection(OrderProjection.class)
                .addProjection(ProductProjection.class)
                .addProjection(OrderProductProjection.class)
                .addProjection(UserRoleProjection.class);

        config.getCorsRegistry()
                .addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
