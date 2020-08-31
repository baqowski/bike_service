package app.core.service.helper;

import app.core.entity.OrderProduct;
import app.core.entity.dto.ProductDTO;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
public class OrderHelper {

    public ProductDTO toProductDTO(OrderProduct orderProduct) {
        return ProductDTO.builder()
                .id(orderProduct.getProduct().getId())
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getProduct().getPrice())
                .quantity(orderProduct.getQuantity())
                .build();
    }
}
