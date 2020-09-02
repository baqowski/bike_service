package app.core.controller;

import app.core.entity.Order;
import app.core.entity.dto.OrderDTO;
import app.core.repository.OrderRepository;
import app.core.repository.ProductRepository;
import app.core.repository.UserRepository;
import app.core.service.OrderService;
import app.core.service.mapper.OrderMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public Long create(@RequestBody OrderDTO orderDTO) {
         return orderMapper.map(orderDTO).getId();
    }

    @GetMapping("{/orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @ExceptionHandler(NotFoundException.class)
    @GetMapping("/{orderId}/products")
    public OrderDTO getOrderProducts(@PathVariable Long orderId) throws NotFoundException {
            return orderService.getUserOrderDTO(orderId);

    }
}
