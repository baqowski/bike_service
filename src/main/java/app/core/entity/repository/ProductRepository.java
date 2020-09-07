package app.core.entity.repository;

import app.core.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

/**
 * @author Karol Bąk
 */

/*@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")*/
@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product, Long> {

    @PreAuthorize("hasRole('ADMIN')")
    List<Product> findAll();

    Product findByName(String name);

    Optional<List<Product>> findAllByProductCategory_CategoryName(String name);

}
