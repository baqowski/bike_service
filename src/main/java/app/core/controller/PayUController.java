package app.core.controller;

import app.core.service.PayUService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karol Bąk
 */

@RestController
@RequestMapping("/api/payu")
@RequiredArgsConstructor
public class PayUController {

    private final PayUService payUService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/order")
    public void createOrder() {
        payUService.createOrder();
    }


}
