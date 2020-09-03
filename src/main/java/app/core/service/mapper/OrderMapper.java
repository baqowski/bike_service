package app.core.service.mapper;

import app.core.entity.*;
import app.core.entity.dto.OrderDTO;
import app.core.entity.type.OrderStatus;
import app.core.repository.*;
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
    private final DeliveryOrderRepository deliveryOrderRepository;

    private CommonMapper<DeliveryAddress> deliveryAddressMapper;

    private final UserService userService;

    public Order map(OrderDTO orderDTO) {
        Order order = new Order();

        List<OrderProduct> orderProducts = productMapper.mapFromDtoToProductList(orderDTO.getProducts(), order);

        Delivery delivery = deliveryRepository.findByType(orderDTO.getDelivery().getType());

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setStreet(orderDTO.getDelivery().getDeliveryAddress().getStreet());
        deliveryAddress.setHouseNumber(orderDTO.getDelivery().getDeliveryAddress().getHouseNumber());
        deliveryAddress.setPostalCode(orderDTO.getDelivery().getDeliveryAddress().getPostalCode());
        deliveryAddress.setCity(orderDTO.getDelivery().getDeliveryAddress().getCity());

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setDelivery(delivery);
        deliveryOrder.setDeliveryAddress(deliveryAddressRepository.save(deliveryAddress));


        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        order.setDeliveryOrder(deliveryOrderRepository.save(deliveryOrder));

        order.setUser(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        order.setProducts(orderProducts);

        order.setAmount(orderHelper.calculateOrderSummaryPrice(order));
        orderRepository.save(order).setProducts((List<OrderProduct>) orderProductRepository.saveAll(orderProducts));
        return order;
    }

    public OrderDTO toOrderDto(Long productId) {
        return  null;
    }
}
