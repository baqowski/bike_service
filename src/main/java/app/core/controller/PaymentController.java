package app.core.controller;

import app.core.entity.dto.PaymentResponseDTO;
import app.core.entity.type.PaymentType;
import app.core.service.PayUService;
import app.core.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karol Bąk
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@PropertySource("classpath:payu.properties")
public class PaymentController {

    private final PaymentService paymentService;
    private final PayUService payUService;


    @PostMapping("/{orderId}/{paymentType}")
    public PaymentResponseDTO createPaymentOrder(@PathVariable Long orderId, @PathVariable PaymentType paymentType) {
        return paymentService.createNewPaymentOrUpdateExisting(orderId, paymentType);
    }

/*    @PatchMapping("/{orderId}")
    public PaymentResponseDTO changePaymentForm(@PathVariable Long orderId, @RequestBody PaymentDTO paymentDTO) {
        return paymentService.updatePayment(orderId, paymentDTO);
    }*/
}
