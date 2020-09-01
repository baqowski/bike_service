package app.core.controller;

import app.core.entity.Payment;
import app.core.entity.dto.OrderDTO;
import app.core.service.PayUService;
import app.core.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karol Bąk
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public void createPayment(OrderDTO orderDTO) {

    }
}
