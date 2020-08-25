package app.core.service.shop;

import app.core.entity.dto.ProductDTO;
import app.core.entity.shop.Product;
import app.core.entity.shop.ProductShoppingCart;
import app.core.entity.shop.ShoppingCart;
import app.core.exception.ShoppingCardProductException;
import app.core.repository.ProductShoppingCartRepository;
import app.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Karol Bąk
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductShoppingCartService {

    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final ProductShoppingCartRepository productShoppingCartRepository;
    private final ShopHelperService shopHelperService;

    @Transactional
    public void addProductToShoppingList(Long productId, ProductDTO productDTO) {
        Product product = productService.getProductById(productId);
        ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCard();
        Optional<ProductShoppingCart> optional = productShoppingCartRepository.findByProductAndShoppingCart(product, shoppingCart);

        optional.ifPresentOrElse(
                value -> {
                    value.setCount(Optional.ofNullable(productDTO.getCount()).orElse(1));
                    shoppingCart.getProductShoppingCarts().add(value);
                    productShoppingCartRepository.save(value);
                },
                () -> {
                    ProductShoppingCart productShoppingCart = new ProductShoppingCart(product, shoppingCart, Optional.ofNullable(productDTO.getCount()).orElse(1));
                    shoppingCart.getProductShoppingCarts().add(productShoppingCart);
                    productShoppingCartRepository.save(productShoppingCart);
                }
        );
        shopHelperService.updateShoppingCard(shoppingCart);
    }

    @Transactional
    public void removeProductFromShoppingList(Long productId) {
        Product product = productService.getProductById(productId);
        ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCard();
        ProductShoppingCart productShoppingCart = productShoppingCartRepository.findByProductAndShoppingCart(product, shoppingCart)
                .orElseThrow(() -> new ShoppingCardProductException("Nie ma takiego porduktu w tym koszyku"));

        shoppingCart.getProductShoppingCarts().remove(productShoppingCart);
        productShoppingCartRepository.delete(productShoppingCart);
        shopHelperService.updateShoppingCard(shoppingCart);
    }


}
