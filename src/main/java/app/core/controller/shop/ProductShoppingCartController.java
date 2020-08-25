package app.core.controller.shop;

import app.core.entity.dto.ProductDTO;
import app.core.service.shop.ProductShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Karol Bąk
 */
@RestController
@RequestMapping("/api/productShoppingCarts")
@RequiredArgsConstructor
public class ProductShoppingCartController {

    private final ProductShoppingCartService productShoppingCartService;

    @PostMapping("/{productId}")
    public void addProductToUserShoppingCart(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        productShoppingCartService.addProductToShoppingList(productId, productDTO);
    }

    @DeleteMapping("/product/{productId}")
    public void removeProduct(@PathVariable Long productId) {
        productShoppingCartService.removeProductFromShoppingList(productId);
    }
}
