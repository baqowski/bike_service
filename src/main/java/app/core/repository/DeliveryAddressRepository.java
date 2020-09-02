package app.core.repository;

import app.core.entity.DeliveryAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Long> {
}
