package app.core.repository;

import app.core.entity.shop.ShoppingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface ShoppingCardRepository extends JpaRepository<ShoppingCard, Long> {

    ShoppingCard findByUser_Username(String username);



}
