package app.core.controller;

import app.core.entity.Product;
import app.core.service.ShoppingBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Karol Bąk
 */

@RepositoryRestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class ShoppingBasketController {

    private final ShoppingBasketService shoppingBasketService;

    @GetMapping("/userProducts")
    public ResponseEntity<List<Product>> getUserBasketProducts() {
        return ResponseEntity.ok(shoppingBasketService.getUserBasketProducts(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PatchMapping("/add/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToShopping(@PathVariable Long productId) {
        shoppingBasketService.addProductToShoppingBasket(productId);
    }
}
