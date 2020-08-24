package app.core.controller;

import app.core.entity.dto.ProductDTO;
import app.core.exception.ProductException;
import app.core.service.ShoppingCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Karol Bąk
 */


@RestController
@RequestMapping("/api/shoppingCard")
@RequiredArgsConstructor
public class ShoppingCardController {

    private final ShoppingCardService shoppingCardService;

    @GetMapping("/products")
    public ResponseEntity<?> getUserBasketProducts() {
        return ResponseEntity.ok(shoppingCardService.getUserProducts());
    }

    @GetMapping("/amount")
    public ResponseEntity<?> getUserShoppingCard() {
        return ResponseEntity.ok(shoppingCardService.getShoppingCard().getAmount());
    }

    @PostMapping("/add/{productId}/{count}")
    public ResponseEntity<?> addProductToShopping(@PathVariable Long productId, @PathVariable Integer count) throws IOException {
        try {
            shoppingCardService.addProductToShoppingCard(productId, count);
            return ResponseEntity.ok().build();
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProductShoppingCard(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(shoppingCardService.updateShoppingCardProduct(productId, productDTO.getCount()));
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProductShoppingCard(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(shoppingCardService.removeProductFromShoppingCard(productId));
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
