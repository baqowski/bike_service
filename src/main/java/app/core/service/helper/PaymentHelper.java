package app.core.service.helper;

import app.core.entity.Payment;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.type.PaymentStatus;
import app.core.exception.payment.PaymentAlreadyFinishedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class PaymentHelper {

    private final PaymentRepository paymentRepository;

    public Payment paymentAlreadyFinished(Long orderId) {
        Payment payment = paymentRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> new PaymentAlreadyFinishedException("Płatność została już wykonana"));
        if (PaymentStatus.FINISHED.equals(payment.getPaymentStatus())) {
            throw new PaymentAlreadyFinishedException("Płatność została już wykonana");
        }
        return null;
    }
}
