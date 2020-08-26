package app.core.service.mapper;

import app.core.entity.dto.ProductDTO;
import app.core.entity.dto.ShoppingCartDTO;
import app.core.entity.shop.ShoppingCart;
import app.core.entity.shop.ProductShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class ProductShoppingCardMapper {

    public ShoppingCartDTO mapToProductListDTO(ShoppingCart shoppingCart) {
               return productListDTO(shoppingCart);
    }

    private ProductDTO toProductDTO(ProductShoppingCart productShoppingCart) {
        return ProductDTO.builder()
                .id(productShoppingCart.getProduct().getId())
                .name(productShoppingCart.getProduct().getName())
                .price(productShoppingCart.getProduct().getPrice())
                .count(productShoppingCart.getCount())
                .build();
    }

    private ShoppingCartDTO productListDTO(ShoppingCart shoppingCart) {
        List<ProductDTO> productDTOS = shoppingCart.getProductShoppingCarts().stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());

        return new ShoppingCartDTO(productDTOS, shoppingCart.getAmount());
    }
}
