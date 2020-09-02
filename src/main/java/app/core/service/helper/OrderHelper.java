package app.core.service.helper;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.User;
import app.core.entity.dto.ProductDTO;
import app.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderHelper {

    private final UserRepository userRepository;

    public ProductDTO toProductDTO(OrderProduct orderProduct) {
        return ProductDTO.builder()
                .id(orderProduct.getProduct().getId())
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getProduct().getPrice())
                .quantity(orderProduct.getQuantity())
                .build();
    }

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
               .add(order.getDelivery().getCost());
    }
}
