package app.core.service.mapper;

import app.core.repository.OrderRepository;
import app.core.service.OrderService;
import app.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    private final UserService userService;

}
