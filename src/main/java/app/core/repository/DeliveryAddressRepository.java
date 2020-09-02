package app.core.repository;

import app.core.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface DeliveryAddressRepository extends CrudRepository<Address, Long> {

    List<Address> getAllByUser_Id(Long userId);
}
