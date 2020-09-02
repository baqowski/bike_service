package app.core.service;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuOrderResponseDTO;
import app.core.entity.type.PaymentType;
import app.core.exception.OrderException;
import app.core.repository.OrderRepository;
import app.core.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
@PropertySource("classpath:payu.properties")
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PayUService payUService;
    private final OrderRepository orderRepository;

    public void createNewPayment(Long orderId) {
        Order order = orderRepository.getById(orderId).orElseThrow(()-> new OrderException("Brak zamówienia o odanym identyfikatorze " + orderId));

        Payment payment = new Payment();

        if (PaymentType.PAYU.equals(order.getPaymentType())) {
            PayuDTO payuDTO = new PayuDTO();
            PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
            payment.setPayuId(payuOrderResponseDTO.getOrderId());
        }
        paymentRepository.save(payment);
    };
}
