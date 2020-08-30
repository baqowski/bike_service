package app.core.repository;

import app.core.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
}
