package app.core.entity.repository;

import app.core.entity.DeliveryOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface DeliveryOrderRepository extends CrudRepository<DeliveryOrder, Long> {
}
