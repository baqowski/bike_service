package app.core.repository;

import app.core.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

/**
 * @author Karol Bąk
 */

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> getAllByUser_Id(Long userId);

    Optional<Order> getByUser_uuidAndId(String uuid, Long orderId);

    Optional<Order> getById(Long id);
}
