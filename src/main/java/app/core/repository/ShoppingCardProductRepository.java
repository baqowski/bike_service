package app.core.repository;


import app.core.entity.shop.Product;
import app.core.entity.shop.ShoppingCard;
import app.core.entity.shop.ShoppingCardProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface ShoppingCardProductRepository extends JpaRepository<ShoppingCardProduct, Long> {

    List<ShoppingCardProduct> findAllByShoppingCard(ShoppingCard shoppingCard);

    Optional<ShoppingCardProduct> findByProduct(Product product);

    ShoppingCardProduct findByProductAndShoppingCard(Product product, ShoppingCard shoppingCard);
}
