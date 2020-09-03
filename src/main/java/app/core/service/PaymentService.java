package app.core.service;

import app.core.entity.Payment;
import app.core.entity.dto.*;
import app.core.entity.type.PaymentStatus;
import app.core.entity.type.PaymentType;
import app.core.exception.OrderException;
import app.core.repository.OrderRepository;
import app.core.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PaymentResponseDTO createNewPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setOrder(orderRepository.getById(paymentDTO.getOrderId())
                .orElseThrow(()-> new OrderException("Brak zamówienia o odanym identyfikatorze " + paymentDTO.getOrderId())));

        payment.setPaymentStatus(PaymentStatus.STARTED);
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

        if (PaymentType.PAYU.equals(paymentDTO.getPaymentType())) {
            PayuDTO payuDTO = payUService.map(paymentDTO.getOrderId());
            PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
            paymentResponseDTO.setRedirectUri(payuOrderResponseDTO.getRedirectUri());
            payment.setPayuOrderId(payuOrderResponseDTO.getOrderId());
        }

        paymentResponseDTO.setPaymentId(payment.getId());
        paymentRepository.save(payment);
        return paymentResponseDTO;
    }
}
