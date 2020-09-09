package app.core.service.helper;

import app.core.entity.Payment;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.type.PaymentStatus;
import app.core.exception.payment.PaymentAlreadyFinishedException;
import app.core.exception.payment.PaymentException;
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
                .orElseThrow(() -> new PaymentException("Nie mozna znalezc płatnosci"));
        if (PaymentStatus.FINISHED.equals(payment.getStatus())) {
            throw new PaymentAlreadyFinishedException("Płatność została już wykonana");
        }
        return null;
    }
}
