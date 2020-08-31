package app.core.repository;

import app.core.entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {

    List<OrderProduct> findAllByOrder_Id(Long id);
}
