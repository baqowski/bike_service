package app.core.controller.ext;

import app.core.entity.Product;
import app.core.exception.ProductException;
import app.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Karol Bąk
 */

@RestController
@RequestMapping("/ext")
@RequiredArgsConstructor
public class ExternalController {

    private final ProductRepository productRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Brak produktu o takim id:" + productId));
    }
}
