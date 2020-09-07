package app.core.controller;

import app.core.entity.dto.ProductDTO;
import app.core.entity.repository.ProductRepository;
import app.core.exception.ProductException;
import app.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Karol Bąk
 */

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<?> get (@PathVariable Long productId) {
        return ResponseEntity.ok(productRepository.findById(productId));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> edit(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        try {
            productService.update(productId, productDTO);
            return ResponseEntity.ok().build();
        } catch (ProductException e) {
            log.info(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable Long productId) {
        try {
            productService.delete(productId);
            return ResponseEntity.ok().build();
        } catch (ProductException e) {
            log.info(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
