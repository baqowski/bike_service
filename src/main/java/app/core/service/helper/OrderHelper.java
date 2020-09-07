package app.core.service.helper;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.User;
import app.core.entity.repository.OrderRepository;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.repository.UserRepository;
import app.core.exception.OrderException;
import app.core.exception.payment.PaymentException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderHelper {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;



    public User getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid).orElseThrow( ()-> new UsernameNotFoundException("Cant find user"));
    }


    public boolean validateOrdersForClients(String uuid, Order target) {
        return getUserByUuid(uuid).getOrders().stream().anyMatch(order -> order.equals(target)) && getUserByUuid(uuid).getRole().getName().equals("ROLE_USER");
    }

    public BigDecimal calculateOrderSummaryPrice(Order order) {
       return order.getProducts().stream()
               .map(orderProduct -> BigDecimal.valueOf(orderProduct.getQuantity()).multiply(orderProduct.getProduct().getPrice()))
               .reduce(BigDecimal.ZERO, BigDecimal::add)
               .add(order.getDeliveryOrder().getDelivery().getCost());
    }

    public Order getOrderById(Long id) {
        return orderRepository.getById(id).orElseThrow(() -> new OrderException("Brak zamówienia o takim numerze " + id));
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentException("Brak płatności o takim numerze"));
    }


    @ExceptionHandler(OrderException.class)
    public void validateOrderPayment(Order order) {

    }
}
