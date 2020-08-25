package app.core.service.shop;

import app.core.entity.dto.ProductListDTO;
import app.core.entity.shop.Product;
import app.core.entity.shop.ShoppingCart;
import app.core.entity.shop.ProductShoppingCart;
import app.core.exception.ProductException;
import app.core.exception.ShoppingCardProductException;
import app.core.repository.ProductRepository;
import app.core.repository.ProductShoppingCartRepository;
import app.core.repository.ShoppingCardRepository;
import app.core.service.mapper.ProductShoppingCardMapper;
import app.jwt.entity.User;
import app.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Karol Bąk
 */

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:translation.properties")
public class ShoppingCartService {

    @Value("${core.entity.product.productNotFound}")
    private String PRODUCT_NOT_FOUND;

    private final ProductRepository productRepository;
    private final ShoppingCardRepository shoppingCardRepository;
    private final ProductShoppingCartRepository productShoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductShoppingCardMapper productShoppingCardMapper;

    public ProductListDTO getShoppingCartProducts() {
        ShoppingCart shoppingCart = getUserShoppingCard();
        return productShoppingCardMapper.mapToProductListDTO(shoppingCart);
    }

    public void addProductToShoppingCard(Long productId, Integer count) throws IOException {
        ShoppingCart userShoppingCart = shoppingCardRepository.findByUser_UsernameAndIsActiveTrue(SecurityContextHolder.getContext().getAuthentication().getName());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND + productId));

        Optional<ProductShoppingCart> shoppingCardProduct = productShoppingCartRepository.findByProduct(product);

        shoppingCardProduct.ifPresentOrElse(
                value -> {
                    value.setCount(count);
                    productShoppingCartRepository.save(value);
                },
                () -> {
                    productShoppingCartRepository.save(new ProductShoppingCart(product, userShoppingCart, count));
                }
        );

        userShoppingCart.setAmount(calculateShoppingCardAmount(userShoppingCart));
        shoppingCardRepository.save(userShoppingCart);
    }

    public BigDecimal updateShoppingCardProduct(Long productId, Integer counter) {
        Product product = getProduct(productId);
        ShoppingCart shoppingCart = getUserShoppingCard();
        ProductShoppingCart productShoppingCart = productShoppingCartRepository.findByProductAndShoppingCart(product, shoppingCart)
                .orElseThrow(() -> new ShoppingCardProductException("Brak takiego produktu w koszyku"));
        productShoppingCart.setCount(counter);
        updateShoppingCard(shoppingCart);
        productShoppingCartRepository.save(productShoppingCart);
        return shoppingCart.getAmount();
    }

    public BigDecimal removeProductFromShoppingCard(Long productId) {
        Product product = getProduct(productId);
        ShoppingCart shoppingCart = getUserShoppingCard();
        ProductShoppingCart productShoppingCart = getUserProductShoppingCard(product, shoppingCart);
       /* shoppingCart.getProducts().remove(productShoppingCart);*/
        /*shoppingCardRepository.save(shoppingCard);*/
        updateShoppingCard(shoppingCart);
        return shoppingCart.getAmount();
    }

    public ShoppingCart getShoppingCard() {
        return getUserShoppingCard();
    }

    public void createNewUserShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCardRepository.save(shoppingCart);
    }

    private BigDecimal calculateShoppingCardAmount(ShoppingCart shoppingCart) {
        return shoppingCart.getProductShoppingCarts().stream()
                .map(shoppingCardProduct -> shoppingCardProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCardProduct.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateShoppingCard(ShoppingCart shoppingCart) {
        shoppingCart.setAmount(calculateShoppingCardAmount(shoppingCart));
        shoppingCardRepository.save(shoppingCart);
    }

    public ShoppingCart getUserShoppingCard() {
        return shoppingCardRepository.findByUser_UsernameAndIsActiveTrue(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND + productId));
    }

    private ProductShoppingCart getUserProductShoppingCard(Product product, ShoppingCart userShoppingCart) {
        return productShoppingCartRepository.findByProductAndShoppingCart(product, userShoppingCart)
                .orElseThrow(() -> new ShoppingCardProductException("Brak takiego produktu w koszyku"));
    }
}
