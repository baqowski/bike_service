package app.core.service.mapper;

import app.core.entity.dto.ProductCardResponse;
import app.core.entity.dto.ProductDTO;
import app.core.entity.dto.ProductListDTO;
import app.core.entity.shop.ShoppingCart;
import app.core.entity.shop.ProductShoppingCart;
import app.core.repository.ShoppingCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class ProductShoppingCardMapper {

    public ProductListDTO mapToProductListDTO(ShoppingCart shoppingCart) {
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

    private ProductListDTO productListDTO(ShoppingCart shoppingCart) {
        List<ProductDTO> productDTOS = shoppingCart.getProductShoppingCarts().stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());

        return new ProductListDTO(productDTOS, shoppingCart.getAmount());
    }
}
