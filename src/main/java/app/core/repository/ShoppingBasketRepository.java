package app.core.repository;

import app.core.entity.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */
@RepositoryRestResource
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {

    ShoppingBasket findByUser_Username(String username);



}
