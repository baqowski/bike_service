package app.core.projection;

import app.core.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

    ProductCategory getByCategoryName(String name);
}
