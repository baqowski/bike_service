package app.core.controller.shop;

import app.core.entity.dto.ProductDTO;
import app.core.exception.ProductException;
import app.core.service.shop.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Karol Bąk
 */


@RestController
@RequestMapping("/api/shoppingCarts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping()
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartProducts());
    }


    @GetMapping("/amount")
    public ResponseEntity<?> getUserShoppingCard() {
        return ResponseEntity.ok(shoppingCartService.getShoppingCard().getAmount());
    }

    @PostMapping("/add/{productId}/{count}")
    public ResponseEntity<?> addProductToShopping(@PathVariable Long productId, @PathVariable Integer count) throws IOException {
        try {
            shoppingCartService.addProductToShoppingCard(productId, count);
            return ResponseEntity.ok().build();
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProductShoppingCard(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        try {
            return ResponseEntity.ok(shoppingCartService.updateShoppingCardProduct(productId, productDTO.getCount()));
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProductShoppingCard(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(shoppingCartService.removeProductFromShoppingCard(productId));
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
