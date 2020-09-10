/*
package app.core.service.mapper;

import app.core.entity.*;
import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.ProductDTO;
import app.core.entity.repository.*;
import app.core.entity.type.OrderStatus;
import app.core.exception.ProductException;
import app.core.service.helper.UserHelper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

*/
/**
 * @author Karol Bąk
 *//*

@Service
@RequiredArgsConstructor
public class OrderMapper implements DtoMapper<Order, OrderDTO> {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final PaymentRepository paymentRepository;
    private final UserHelper userHelper;



  */
/*  @Override
    @Transactional
    public Order toEntity(OrderDTO dto) throws NotFoundException {
        Order order = new Order();
        orderRepository.save(order);
        toOrderProductList(dto.getProducts(), order);

        Delivery delivery = deliveryRepository.findById(dto.getDeliveryOrder().getDelivery().getId()).orElseThrow(() -> new NotFoundException("adasda"));
        DeliveryAddress deliveryAddress = new DeliveryAddress(dto.getDeliveryOrder().getDeliveryAddress());

        DeliveryOrder deliveryOrder = new DeliveryOrder(delivery, deliveryAddressRepository.save(deliveryAddress));

        order.setDeliveryOrder(deliveryOrderRepository.save(deliveryOrder));
        order.setAmount(dto.getAmount());
        order.setOrderStatus(OrderStatus.CREATED_BY_CLIENT);
        order.setUser(userHelper.getUserFormSecurityContext());
        return order;
    }*//*




    public OrderDTO mapToDTO (Order order) {
        return null;
    }

  */
/*  private Product toProduct(ProductDTO productDTO) {
        return productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductException("Cant find product by id:" + productDTO.getId()));
    }

    private List<OrderProduct> toOrderProductList(List<ProductDTO> productDTOS, Order order) {
        return productDTOS.stream().map(productDTO -> this.toOrderProduct(productDTO, order))
                .collect(Collectors.toList());
    }

    private OrderProduct toOrderProduct(ProductDTO productDTO, Order order) {
        return orderProductRepository.save(new OrderProduct(toProduct(productDTO), order, productDTO.getQuantity()));
    }*//*



}
*/
