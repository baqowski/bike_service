package app.core.repository;

import app.core.entity.Delivery;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface DeliveryRepository extends PagingAndSortingRepository<Delivery, Long> {

    Delivery findByType (String type);
}
