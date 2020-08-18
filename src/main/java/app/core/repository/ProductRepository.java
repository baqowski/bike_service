package app.core.repository;

import app.core.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * @author Karol Bąk
 */

@CrossOrigin(origins = "http://localhost:4200")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    List<Product> findAll();
}
