package app.core.service.mapper;

import app.core.entity.dto.ProductCardResponse;
import app.core.entity.dto.ProductListDTO;
import app.core.entity.shop.ShoppingCard;
import app.core.entity.shop.ShoppingCardProduct;
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
public class ShoppingCardProductMapper {

    private final ShoppingCardRepository shoppingCardRepository;

    public ProductListDTO map() {
        ShoppingCard shoppingCard = shoppingCardRepository.findByUser_Username(SecurityContextHolder.getContext().getAuthentication().getName());
        return productListDTO(shoppingCard);
    }

    private ProductCardResponse toProductCardResponse(ShoppingCardProduct shoppingCardProduct) {
        return ProductCardResponse.builder()
                .id(shoppingCardProduct.getProduct().getId())
                .name(shoppingCardProduct.getProduct().getName())
                .price(shoppingCardProduct.getProduct().getPrice())
                .count(shoppingCardProduct.getCount())
                .build();
    }

    private ProductListDTO productListDTO(ShoppingCard shoppingCard) {
        List<ProductCardResponse> cardResponses = shoppingCard.getProducts().stream()
                .map(this::toProductCardResponse)
                .collect(Collectors.toList());

        return new ProductListDTO(cardResponses, shoppingCard.getAmount());
    }
}
