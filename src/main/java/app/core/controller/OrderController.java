package app.core.controller;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.entity.repository.OrderRepository;
import app.core.service.OrderService;
import app.core.service.mapper.payu.OrderMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final OrderMapper orderMapper;

    @PostMapping
    public Long registerNewOrderWithEmptyPayment(@RequestBody OrderDTO orderDTO) {
        return orderService.registerNewOrder(orderDTO).getId();
    }

    @GetMapping
    public List<OrderDTO> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this.orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO order) {
        orderService.updateOrder(orderId, order);
    }

    @GetMapping("/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        return this.orderMapper.toDto(orderService.getOrderForUserRole(orderId));
    }

    @ExceptionHandler(NotFoundException.class)
    @GetMapping("/{orderId}/products")
    public OrderDTO getOrderProducts(@PathVariable Long orderId) throws NotFoundException {
        /*return orderService.getUserOrderDTO(orderId);*/
        return null;
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    public List<Order> getUserOrders(@PathVariable String uuid) {
        return orderRepository.findAllByUser_Uuid(uuid);
    }*/

    @PostMapping("/{orderId}/payments")
    public Long getNewPaymentIdCreatedByPaymentType(@PathVariable Long orderId, @RequestBody Payment payment) {
        return orderService.createOrUpdateOrderPayment(orderId, payment.getType()).getId();
    }

    @GetMapping("/{orderId}/payments/{paymentId}")
    public Payment getOrderPayment(@PathVariable Long orderId, @PathVariable Long paymentId) {
        return orderService.getOrderPayment(orderId, paymentId);
    }
}
