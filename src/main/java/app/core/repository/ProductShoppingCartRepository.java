package app.core.repository;


import app.core.entity.shop.Product;
import app.core.entity.shop.ShoppingCart;
import app.core.entity.shop.ProductShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

/**
 * @author Karol Bąk
 */
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RepositoryRestResource
public interface ProductShoppingCartRepository extends JpaRepository<ProductShoppingCart, Long> {

    Optional<ProductShoppingCart> findByProduct(Product product);

    Optional<ProductShoppingCart> findByProductAndShoppingCart(Product product, ShoppingCart shoppingCart);
}
