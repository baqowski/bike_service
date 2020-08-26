package app.core.service.shop;

import app.core.entity.shop.ShoppingCart;
import app.core.repository.ShoppingCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class ShopHelperService {

    private final ShoppingCardRepository shoppingCardRepository;

    public ShoppingCart updateShoppingCard(ShoppingCart shoppingCart) {
        shoppingCart.setAmount(calculateAmountShoppingCard(shoppingCart));
        return shoppingCardRepository.save(shoppingCart);
    }

    private BigDecimal calculateAmountShoppingCard(ShoppingCart shoppingCart) {
        return shoppingCart.getProductShoppingCarts().stream()
                .map(shoppingCardProduct -> shoppingCardProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCardProduct.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
