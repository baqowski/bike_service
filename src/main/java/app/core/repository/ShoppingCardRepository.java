package app.core.repository;

import app.core.entity.shop.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Karol Bąk
 */
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RepositoryRestResource
public interface ShoppingCardRepository extends PagingAndSortingRepository<ShoppingCart, Long> {

    ShoppingCart findByUser_UsernameAndIsActiveTrue(String username);



}
