package app.core.service;

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

    public void createNewPayment(OrderDTO orderDTO) {
        Payment payment = new Payment();
        payment.setOrder(orderRepository.getById(orderDTO.getId())
                .orElseThrow(()-> new OrderException("Brak zamówienia o odanym identyfikatorze " + orderDTO.getId())));

        payment.setPaymentType(orderDTO.getPaymentType());
        paymentRepository.save(payment);

        if (PaymentType.PAYU.equals(orderDTO.getPaymentType())) {
            PayuDTO payuDTO = new PayuDTO();
            PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
        }
    };
}
