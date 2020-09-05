package app.core.service;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.dto.PaymentDTO;
import app.core.entity.dto.PaymentResponseDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuOrderResponseDTO;
import app.core.entity.type.PaymentType;
import app.core.exception.OrderException;
import app.core.repository.PaymentRepository;
import app.core.service.helper.OrderHelper;
import app.core.service.helper.PaymentHelper;
import app.core.service.mapper.DtoMapper;
import app.core.service.mapper.payu.PayuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
@PropertySource("classpath:payu.properties")
public class PaymentService implements DtoMapper<PaymentDTO, Payment> {

    private final PaymentRepository paymentRepository;
    private final PayUService payUService;
    private final PayuMapper payuMapper;
    private final OrderHelper orderHelper;
    private final PaymentHelper paymentHelper;

    @Transactional
    public PaymentResponseDTO createNewPayment(Long orderId, PaymentType paymentType) {
        determinePayment(orderId, paymentType);

        Order order = orderHelper.getOrderById(orderId);
        Payment payment = order.getPayment();

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        if (PaymentType.PAYU.equals(paymentType)) {
            PayuDTO payuDTO = payuMapper.map(order);
            PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
            paymentResponseDTO.setRedirectUri(payuOrderResponseDTO.getRedirectUri());
            payment.setPayuOrderId(payuOrderResponseDTO.getOrderId());
        }

        paymentRepository.save(payment);
        paymentResponseDTO.setPaymentId(payment.getId());
        return paymentResponseDTO;
    }


    public Payment getOrderPayment(Long orderId) {
        return paymentRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> new OrderException("Brak płatności dla tego zamówienia"));
    }

    @Override
    public PaymentDTO map(Payment payment) {
        return PaymentDTO.builder()
                .orderId(payment.getId())
                .payuOrderId(payment.getPayuOrderId())
                .paymentType(payment.getPaymentType())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }


    private void updateExistingPayment(Payment payment, PaymentType type) {
        payment.setPaymentType(type);
        paymentRepository.save(payment);
    }

    private void createNewPayment(Order order, PaymentType paymentType) {
        Payment payment = new Payment(order, paymentType);
        paymentRepository.save(payment);
    }

    private void determinePayment(Long orderId, PaymentType paymentType) {
        paymentHelper.paymentAlreadyFinished(orderId);
        Order order = orderHelper.getOrderById(orderId);
        Optional.ofNullable(order.getPayment()).ifPresentOrElse(
                (value) -> {
                    updateExistingPayment(value, paymentType);
                },
                () -> {
                    createNewPayment(order, paymentType);
                }
        );
    }


}
