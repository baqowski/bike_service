package app.core.service.mapper;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.dto.ProductDTO;
import app.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductService productService;

    public List<OrderProduct> mapFromDtoToProductList(List<ProductDTO> productDTOList, Order order) {
        return productDTOList.stream().map(productDTO -> this.toOrderProduct(productDTO, order))
                .collect(Collectors.toList());
    }

    private OrderProduct toOrderProduct(ProductDTO productDTO,Order order) {
        return new OrderProduct(productService.getProductById(productDTO.getId()), order, productDTO.getQuantity());
    }
}
