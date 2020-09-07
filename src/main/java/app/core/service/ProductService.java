package app.core.service;

import app.core.entity.Product;
import app.core.entity.dto.ProductDTO;
import app.core.entity.repository.ProductRepository;
import app.core.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void update(Long productId, ProductDTO productDTO) {
        Product product = getProductById(productId);
        /*product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());*/
        productRepository.save(product);

    }

    public void delete(Long productId) {
        productRepository.delete(getProductById(productId));
    }


    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Nie mozna znaleźć produktu o podanym id " + productId));
    }
}
