package app.core.entity.repository;

import app.core.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> getAllByUser_Id(Long userId);

    Optional<Order> getByUser_uuidAndId(String uuid, Long orderId);

    Optional<Order> getById(Long id);

    List<Order> findAllByUser_Uuid(String uuid);

    /*List<Order> findAll();*/
}
