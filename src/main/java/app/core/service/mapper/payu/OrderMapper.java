package app.core.service.mapper.payu;

import app.core.entity.*;
import app.core.entity.dto.*;
import app.core.entity.repository.*;
import app.core.entity.type.OrderStatus;
import app.core.exception.DeliveryException;
import app.core.exception.ProductException;
import app.core.service.helper.PaymentHelper;
import app.core.service.helper.UserHelper;
import app.core.service.mapper.DtoMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderMapper implements DtoMapper<Order, OrderDTO> {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final UserHelper userHelper;
    private final PaymentHelper paymentHelper;
    private final OrderRepository orderRepository;


    @Override
    public Order toEntity(OrderDTO dto) {
        Order order = new Order();

        Delivery delivery = deliveryRepository.findById(dto.getDeliveryOrder().getDelivery().getId()).orElseThrow(() -> new DeliveryException("Not found delivery with this id"));
        DeliveryAddress deliveryAddress = new DeliveryAddress(dto.getDeliveryOrder().getDeliveryAddress());

        DeliveryOrder deliveryOrder = new DeliveryOrder(delivery, deliveryAddressRepository.save(deliveryAddress));

        order.setDeliveryOrder(deliveryOrderRepository.save(deliveryOrder));
        order.setAmount(dto.getAmount());
        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        order.setUser(userHelper.getUserFormSecurityContext());
        orderRepository.save(order);
        toOrderProductList(dto.getProducts(), order);
        return order;
    }

    @Override
    public OrderDTO toDto(Order order) {
        Payment payment = paymentHelper.getPaymentById(order.getId());

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDeliveryOrder(doDeliveryOrderDTO(order.getDeliveryOrder()));
        orderDTO.setPayment(toPaymentDTO(payment));
        orderDTO.setProducts(toProductDtoList(order));
        orderDTO.setOrderServiceType(order.getOrderServiceType());
        orderDTO.setAmount(order.getAmount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        order.setOrderStatus(order.getOrderStatus());

        return orderDTO;
    }

    private Product toProduct(ProductDTO productDTO) {
        return productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductException("Cant find product by id:" + productDTO.getId()));
    }

    private List<OrderProduct> toOrderProductList(List<ProductDTO> productDTOS, Order order) {
        return productDTOS.stream().map(productDTO -> this.toOrderProduct(productDTO, order))
                .collect(Collectors.toList());
    }

    private OrderProduct toOrderProduct(ProductDTO productDTO, Order order) {
        return orderProductRepository.save(new OrderProduct(toProduct(productDTO), order, productDTO.getQuantity()));
    }

    private PaymentDTO toPaymentDTO(Payment payment) {
        return PaymentDTO.builder()
                .paymentStatus(payment.getStatus())
                .paymentType(payment.getType())
                .payuOrderId(payment.getPayuOrderId())
                .id(payment.getId())
                .build();

    }

    private DeliveryOrderDTO doDeliveryOrderDTO(DeliveryOrder deliveryOrder){
        DeliveryDTO deliveryDTO = new DeliveryDTO(deliveryOrder.getDelivery().getType(), deliveryOrder.getDelivery().getCost());
        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder()
                .city(deliveryOrder.getDeliveryAddress().getCity())
                .street(deliveryOrder.getDeliveryAddress().getStreet())
                .houseNumber(deliveryOrder.getDeliveryAddress().getHouseNumber())
                .postalCode(deliveryOrder.getDeliveryAddress().getPostalCode())
                .country(Optional.ofNullable(deliveryOrder.getDeliveryAddress().getCountry()).orElse(""))
                .build();
        return new DeliveryOrderDTO(deliveryDTO, deliveryAddressDTO);
    }

    private List<ProductDTO> toProductDtoList(Order order) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder_Id(order.getId());
        return orderProducts.stream().map(this::mapOrderProductToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO mapOrderProductToProductDTO(OrderProduct orderProduct) {
        return ProductDTO.builder()
                .id(orderProduct.getProduct().getId())
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getProduct().getPrice())
                .quantity(orderProduct.getQuantity())
                .build();
    }
}
