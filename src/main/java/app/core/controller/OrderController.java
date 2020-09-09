package app.core.controller;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.entity.repository.OrderRepository;
import app.core.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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
        try {
            return orderService.registerNewOrder(orderDTO);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        //toDo
        return null;
    }

    @GetMapping
    public List<Order> getAll() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Order> tmp = (List<Order>) orderRepository.findAll();
        System.out.println(objectMapper.writeValueAsString(tmp));
        return (List<Order>) orderRepository.findAll();
    }

    @PostAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    public List<Order> getUserOrders(@PathVariable String uuid) {
        return orderRepository.findAllByUser_Uuid(uuid);
    }

    @PostMapping("/{orderId}/payments")
    public Long getNewPaymentIdCreatedByPaymentType(@PathVariable Long orderId, @RequestBody Payment payment) {
        return orderService.createOrUpdateOrderPayment(orderId, payment.getType()).getId();
    }

    @GetMapping("/{orderId}/payments/{paymentId}")
    public Payment getOrderPayment(@PathVariable Long orderId, @PathVariable Long paymentId) {
        return orderService.getOrderPayment(orderId, paymentId);
    }
}
