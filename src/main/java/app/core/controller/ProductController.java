package app.core.controller;

import app.core.entity.Product;
import app.core.entity.dto.ProductDTO;
import app.core.repository.ProductRepository;
import app.core.service.ShoppingBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Karol Bąk
 */

/*@RepositoryRestController
@RequestMapping("/product")
@RequiredArgsConstructor*/
public class ProductController {

/*    private final ProductRepository productRepository;
    private final ShoppingBasketService shoppingBasketService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody ProductDTO productDTO) {
        productRepository.save(shoppingBasketService.addProductToBasket(productDTO));
    }*/


}
