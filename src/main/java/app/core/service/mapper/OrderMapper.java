package app.core.service.mapper;

import app.core.entity.Delivery;
import app.core.entity.DeliveryAddress;
import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.dto.OrderDTO;
import app.core.entity.type.OrderStatus;
import app.core.repository.DeliveryAddressRepository;
import app.core.repository.DeliveryRepository;
import app.core.repository.OrderProductRepository;
import app.core.repository.OrderRepository;
import app.core.service.OrderService;
import app.core.service.UserService;
import app.core.service.helper.OrderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductMapper productMapper;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OrderHelper orderHelper;

    private CommonMapper<DeliveryAddress> deliveryAddressMapper;

    private final UserService userService;

    public Order map(OrderDTO orderDTO) {
        Order order = new Order();

        List<OrderProduct> orderProducts = productMapper.mapFromDtoToProductList(orderDTO.getProducts(), order);

        Delivery delivery = deliveryRepository.findByType(orderDTO.getDelivery().getType());
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .delivery(delivery)
                .build();

        deliveryAddress.setStreet(orderDTO.getDelivery().getDeliveryAddress().getStreet());
        deliveryAddress.setHouseNumber(orderDTO.getDelivery().getDeliveryAddress().getHouseNumber());
        deliveryAddress.setPostalCode(orderDTO.getDelivery().getDeliveryAddress().getPostalCode());
        deliveryAddress.setCity(orderDTO.getDelivery().getDeliveryAddress().getCity());

        delivery.setDeliveryAddress(deliveryAddressRepository.save(deliveryAddress));
        order.setProducts(orderProducts);


        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        order.setDelivery(delivery);

        order.setUser(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        order.setAmount(orderHelper.calculateOrderSummaryPrice(order));
        orderRepository.save(order).setProducts((List<OrderProduct>) orderProductRepository.saveAll(orderProducts));
        return order;
    }

}
