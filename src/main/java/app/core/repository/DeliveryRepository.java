package app.core.repository;

import app.core.entity.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    Delivery findByType (String type);
}
