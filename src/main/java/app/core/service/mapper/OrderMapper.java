package app.core.service.mapper;

import app.core.entity.*;
import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.ProductDTO;
import app.core.entity.type.OrderStatus;
import app.core.exception.ProductException;
import app.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderMapper implements DtoMapper<Order, OrderDTO> {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public Order map(OrderDTO dto) {
        Order order = new Order();

        toOrderProductList(dto.getProducts(), order);

        DeliveryOrder deliveryOrder = new DeliveryOrder(dto.getDeliveryOrder().getDelivery(),
                deliveryAddressRepository.save(dto.getDeliveryOrder().getDeliveryAddress()));

        order.setDeliveryOrder(deliveryOrderRepository.save(deliveryOrder));

        Payment payment = new Payment();
        order.setAmount(dto.getAmount());
        order.setPayment(paymentRepository.save(payment));
        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        return orderRepository.save(order);
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
}
