package app.core.controller;

import app.core.entity.Product;
import app.core.entity.ShoppingBasket;
import app.core.service.ShoppingBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Karol Bąk
 */

@RepositoryRestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final ShoppingBasketService shoppingBasketService;

    @GetMapping("/userProducts")
    public ResponseEntity<List<Product>> getUserBasketProducts() {
        return ResponseEntity.ok(shoppingBasketService.getUserBasketProducts(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
