package app.core.service;

import app.core.entity.Product;
import app.core.entity.ShoppingBasket;
import app.core.repository.ProductRepository;
import app.core.repository.ShoppingBasketRepository;
import app.jwt.entity.User;
import app.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol Bąk
 */

@Service
@RequiredArgsConstructor
public class ShoppingBasketService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;

    public void createUserShoppingBasket(String username) {
        User user =  userRepository.findByUsername(username);
        ShoppingBasket basket = new ShoppingBasket();
        basket.setUser(user);
        basket.setProducts(new ArrayList<>());
        shoppingBasketRepository.save(basket);
    }

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
