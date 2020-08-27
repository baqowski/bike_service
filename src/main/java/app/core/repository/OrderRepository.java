package app.core.repository;

import app.core.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface OrderRepository extends CrudRepository<Order, Long> {
}
