package app.core.service;

import app.core.entity.Product;
import app.core.entity.ShoppingBasket;
import app.core.repository.ProductRepository;
import app.core.repository.ShoppingBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Karol Bąk
 */

@Service
@RequiredArgsConstructor
public class ShoppingBasketService {

    private final ProductRepository productRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;

    public List<Product> getUserBasketProducts(String username) {
        return shoppingBasketRepository.findByUser_Username(username).getProducts();
    }

    public void addProductToShoppingBasket(Long productId) {
        ShoppingBasket shoppingBasket = shoppingBasketRepository.findByUser_Username(SecurityContextHolder.getContext().getAuthentication().getName());
        Product product = productRepository.findById(productId).get();
        shoppingBasket.getProducts().add(product);
        shoppingBasket.updateAmount();
        shoppingBasketRepository.save(shoppingBasket);
    }
}
