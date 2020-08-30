package app.core.controller;

import app.core.entity.dto.ProductDTO;
import app.core.repository.ProductRepository;
import app.core.repository.UserRepository;
import app.core.service.OrderService;
import app.core.service.mapper.ProductMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Karol Bąk
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapperService productMapperService;
    private final OrderService orderService;

    @PostMapping
    public void create(@RequestBody List<ProductDTO> productDTOS) {
        orderService.create(productDTOS);
    }
}
