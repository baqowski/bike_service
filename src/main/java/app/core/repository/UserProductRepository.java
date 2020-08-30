package app.core.repository;

import app.core.entity.UserProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface UserProductRepository extends CrudRepository<UserProduct, Long> {
}
