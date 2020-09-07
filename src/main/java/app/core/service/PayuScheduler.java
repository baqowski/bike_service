package app.core.service;

import app.core.entity.Payment;
import app.core.entity.dto.PayuStatusDTO;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Karol Bąk
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PayuScheduler {

    private final PayUService payUService;
    private final PaymentRepository paymentRepository;

    @Scheduled(cron="*/5 * * * * ?")
    public void checkPaymentStatus() {
        log.info("payu scheduler...");

        List<Payment> paymentList = (List<Payment>) paymentRepository.findAll();
        paymentList.forEach(payment -> {
            if (payment.getPayuOrderId() != null && !PaymentStatus.FINISHED.equals(payment.getPaymentStatus())) {
                ResponseEntity<PayuStatusDTO> payuStatusDTO = payUService.checkOrderStatus(payment.getPayuOrderId());
                if ("SUCCESS".equals(Objects.requireNonNull(payuStatusDTO.getBody()).getStatus().getStatusCode())) {
                    payment.setPaymentStatus(PaymentStatus.FINISHED);
                    paymentRepository.save(payment);
                }
            }
        });
    }

}
