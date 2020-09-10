package app.core.service;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.ProductDTO;
import app.core.entity.repository.OrderProductRepository;
import app.core.entity.repository.OrderRepository;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.type.PaymentType;
import app.core.exception.ForbiddenException;
import app.core.service.helper.OrderHelper;
import app.core.service.helper.UserHelper;
import app.core.service.mapper.payu.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderProductRepository orderProductRepository;
    private final OrderHelper orderHelper;
    private final OrderMapper orderMapper;
    private final UserHelper userHelper;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;



    @Transactional
    public Order registerNewOrder(OrderDTO orderDTO) {
       return orderMapper.toEntity(orderDTO);
    }

    @Transactional
    public Payment createOrUpdateOrderPayment(Long orderId, PaymentType paymentType) {
        Payment payment = new Payment(paymentType);
        paymentRepository.findByOrder_Id(orderId).ifPresentOrElse(
                (value) -> {
                    value.setType(paymentType);
                    BeanUtils.copyProperties(value, payment);
                },
                () -> {
                    Order order = orderHelper.getOrderById(orderId);
                    payment.setOrder(order);
                }
        );
        return paymentRepository.save(payment);
    }


    public Payment getOrderPayment(Long orderId, Long paymentId) {
        Order order = orderHelper.getOrderById(orderId);
        Payment payment = orderHelper.getPaymentById(paymentId);

        if ("ROLE_USER".equals(userHelper.getUserFormSecurityContext().getRole().getName()) &&
                !payment.getId().equals(order.getPayment().getId())) {
            throw new ForbiddenException("Brak uprawnień");
        }
        return payment;
    }


  /*  @Transactional
    public OrderDTO getUserOrderDTO(Long orderId) throws NotFoundException {
        return this.toOrderDTO(orderId);
    }*/



    private List<OrderProduct> getOrderProducts(Long orderId) {
        return orderProductRepository.findAllByOrder_Id(orderId);
    }

    /*private OrderDTO toOrderDTO(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(""));
        return OrderDTO.builder()
                .id(order.getId())
                .amount(order.getAmount())
                .products(this.toProductDTOS(getOrderProducts(orderId)))
                .build();
    }*/



    private ProductDTO toProductDTO(OrderProduct orderProduct) {
        return ProductDTO.builder()
                .id(orderProduct.getProduct().getId())
                .name(orderProduct.getProduct().getName())
                .price(orderProduct.getProduct().getPrice())
                .quantity(orderProduct.getQuantity())
                .build();
    }

}
