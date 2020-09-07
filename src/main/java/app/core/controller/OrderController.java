package app.core.controller;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.entity.repository.OrderRepository;
import app.core.service.OrderService;
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

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @PostMapping
    public Long registerNewOrderWithEmptyPayment(@RequestBody OrderDTO orderDTO) {
       return orderService.registerNewOrder(orderDTO);
    }

    @GetMapping("{/orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @ExceptionHandler(NotFoundException.class)
    @GetMapping("/{orderId}/products")
    public OrderDTO getOrderProducts(@PathVariable Long orderId) throws NotFoundException {
        /*return orderService.getUserOrderDTO(orderId);*/
        return null;
    }

    @PostMapping("/{orderId}/payments")
    public Long getNewPaymentIdCreatedByPaymentType(@PathVariable Long orderId, @RequestBody Payment payment) {
        return orderService.createOrUpdateOrderPayment(orderId, payment.getPaymentType()).getId();
    }

    @GetMapping("/{orderId}/payments/{paymentId}")
    public Payment getOrderPayment(@PathVariable Long orderId, @PathVariable Long paymentId) {
        return orderService.getOrderPayment(orderId, paymentId);
    }
}
