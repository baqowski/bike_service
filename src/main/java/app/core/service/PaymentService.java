package app.core.service;

import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.PayUDTO;
import app.core.entity.dto.PaymentDTO;
import app.core.entity.type.PaymentType;
import app.core.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PayUService payUService;

    public Payment createNewPayment(OrderDTO orderDTO) {

        if (PaymentType.PAYU.equals(orderDTO.getPaymentType())) {
            payUService.authorizeWithPayU();
        }

        Payment payment = new Payment();
        return paymentRepository.save(payment);
    };
}
