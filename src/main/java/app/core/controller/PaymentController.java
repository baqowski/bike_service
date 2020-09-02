package app.core.controller;

import app.core.entity.dto.OrderDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuOrderResponseDTO;
import app.core.entity.dto.PayuProductDTO;
import app.core.service.PayUService;
import app.core.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author Karol Bąk
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@PropertySource("classpath:payu.properties")
public class PaymentController {

    private final PaymentService paymentService;
    private final PayUService payUService;

    @PostMapping
    public void createPayment(Long orderId) {
        paymentService.createNewPayment(orderId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @PostMapping("/test")
    public PayuOrderResponseDTO test () {
        PayuDTO payuDTO = new PayuDTO();
        payuDTO.setNotifyUlr("https://your.eshop.com/notify");
        payuDTO.setCustomerIp("127.0.0.1");
        payuDTO.setMerchantPosId("393517");
        payuDTO.setDescription("Test");
        payuDTO.setCurrencyCode("PLN");
        payuDTO.setTotalAmount("1");
        payuDTO.setProducts(Arrays.asList(new PayuProductDTO("test", "1", "1")));

        PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
        return  payuOrderResponseDTO;
    }
}
