package app.core.service.mapper;

import app.core.entity.Product;
import app.core.entity.dto.ProductListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class ProductMapperService {


    public List<Product> getProducts(ProductListDTO productListDTO) {
        return null;
    }


 /*   public ShoppingCartDTO mapToProductListDTO(ShoppingCart shoppingCart) {
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
    }*/
}
