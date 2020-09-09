package app.core.service.helper;

import app.core.entity.Payment;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.type.PaymentStatus;
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

      /*  if (PaymentStatus.FINISHED.equals(payment.getStatus())) {
            throw new PaymentAlreadyFinishedException("Płatność została już wykonana");
        }*/
        return null;
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findByOrder_Id(paymentId)
                .orElseThrow(() -> new PaymentException("Nie można znaleźć płatności"));
    }

    public void validate(Payment payment) {
        if (PaymentStatus.FINISHED.equals(payment.getStatus())) {
            throw new PaymentException("Płatność została już zrealizowana");
        }
    }
}
