package app.core.service;

import app.core.entity.dto.ProductListDTO;
import app.core.entity.shop.Product;
import app.core.entity.shop.ShoppingCard;
import app.core.entity.shop.ShoppingCardProduct;
import app.core.exception.ProductException;
import app.core.repository.ProductRepository;
import app.core.repository.ShoppingCardProductRepository;
import app.core.repository.ShoppingCardRepository;
import app.core.service.mapper.ShoppingCardProductMapper;
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
public class ShoppingCardService {

    @Value("${core.entity.product.productNotFound}")
    private String PRODUCT_NOT_FOUND;

    private final ProductRepository productRepository;
    private final ShoppingCardRepository shoppingCardRepository;
    private final ShoppingCardProductRepository shoppingCardProductRepository;
    private final UserRepository userRepository;
    private final ShoppingCardProductMapper shoppingCardProductMapper;

    public void addProductToShoppingCard(Long productId, Integer count) throws IOException {
        ShoppingCard userShoppingCard = shoppingCardRepository.findByUser_Username(SecurityContextHolder.getContext().getAuthentication().getName());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND + productId));

        Optional<ShoppingCardProduct> shoppingCardProduct = shoppingCardProductRepository.findByProduct(product);

        shoppingCardProduct.ifPresentOrElse(
                value -> {
                    value.setCount(count);
                    shoppingCardProductRepository.save(value);
                },
                () -> {
                    shoppingCardProductRepository.save(new ShoppingCardProduct(product, userShoppingCard, count));
                }
        );

        userShoppingCard.setAmount(calculateShoppingCardAmount(userShoppingCard));
        shoppingCardRepository.save(userShoppingCard);
    }

    public BigDecimal updateShoppingCardProduct(Long productId, Integer counter) {
        Product product = getProduct(productId);
        ShoppingCard shoppingCard = getUserShoppingCard();
        ShoppingCardProduct shoppingCardProduct = shoppingCardProductRepository.findByProductAndShoppingCard(product, shoppingCard);
        shoppingCardProduct.setCount(counter);
        updateShoppingCard(shoppingCard);
        shoppingCardProductRepository.save(shoppingCardProduct);
        return shoppingCard.getAmount();
    }

    public BigDecimal removeProductFromShoppingCard(Long productId) {
        Product product = getProduct(productId);
        ShoppingCard shoppingCard = getUserShoppingCard();
        ShoppingCardProduct shoppingCardProduct = getUserProductShoppingCard(product, shoppingCard);
        shoppingCard.getProducts().remove(shoppingCardProduct);
        /*shoppingCardRepository.save(shoppingCard);*/
        updateShoppingCard(shoppingCard);
        return shoppingCard.getAmount();
    }

    public ShoppingCard getShoppingCard() {
        return getUserShoppingCard();
    }

    public ProductListDTO getUserProducts() {
        return shoppingCardProductMapper.map();
    }

    public void createNewUserShoppingCard(User user) {
        ShoppingCard shoppingCard = new ShoppingCard();
        shoppingCard.setUser(userRepository.save(user));
        shoppingCardRepository.save(shoppingCard);
    }

    private BigDecimal calculateShoppingCardAmount(ShoppingCard shoppingCard) {
        return shoppingCard.getProducts().stream()
                .map(shoppingCardProduct -> shoppingCardProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCardProduct.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateShoppingCard(ShoppingCard shoppingCard) {
        shoppingCard.setAmount(calculateShoppingCardAmount(shoppingCard));
        shoppingCardRepository.save(shoppingCard);
    }

    private ShoppingCard getUserShoppingCard() {
        return shoppingCardRepository.findByUser_Username(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND + productId));
    }

    private ShoppingCardProduct getUserProductShoppingCard(Product product, ShoppingCard userShoppingCard) {
        return shoppingCardProductRepository.findByProductAndShoppingCard(product, userShoppingCard);

    }

}
