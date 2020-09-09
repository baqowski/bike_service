package app.core.controller.ext;

import app.core.entity.Product;
import app.core.entity.repository.ProductRepository;
import app.core.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Karol Bąk
 */

@RestController
@RequestMapping("/ext/products")
@RequiredArgsConstructor
public class ProductExternalController {

    private final ProductRepository productRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Brak produktu o takim id:" + productId));
        return product;
    }

    @GetMapping("/search/{categoryName}")
    public List<Product> getProductsByCategories(@PathVariable String categoryName) {
        return productRepository.findAllByProductCategory_CategoryName(categoryName).orElseThrow(() -> new ProductException("Brak produktów dla takiej kategori"));
    }


}
