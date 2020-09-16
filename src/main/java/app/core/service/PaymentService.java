package app.core.service;

import app.core.entity.Order;
import app.core.entity.Payment;
import app.core.entity.dto.PaymentDTO;
import app.core.entity.dto.PaymentResponseDTO;
import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuOrderResponseDTO;
import app.core.entity.repository.PaymentRepository;
import app.core.entity.type.PaymentStatus;
import app.core.entity.type.PaymentType;
import app.core.service.helper.OrderHelper;
import app.core.service.helper.PaymentHelper;
import app.core.service.helper.UserHelper;
import app.core.service.mapper.payu.PayuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PayUService payUService;
    private final PayuMapper payuMapper;
    private final OrderHelper orderHelper;
    private final PaymentHelper paymentHelper;
    private final UserHelper userHelper;

    @Transactional
    public PaymentResponseDTO createNewPaymentOrUpdateExisting(Long orderId, PaymentType paymentType) {
        checkIfExist(orderId, paymentType);

        Order order = orderHelper.getOrderById(orderId);
       /* order.setUser(userHelper.getUserFormSecurityContext());*/
        Payment payment = orderHelper.getPaymentByOrderId(orderId);

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        if (PaymentType.PAYU.equals(paymentType)) {
            PayuDTO payuDTO = payuMapper.map(order);
            PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
            paymentResponseDTO.setRedirectUri(payuOrderResponseDTO.getRedirectUri());
            payment.setPayuOrderId(payuOrderResponseDTO.getOrderId());
        }

        payment.setStatus(PaymentStatus.STARTED);
        paymentRepository.save(payment);
        paymentResponseDTO.setPaymentId(payment.getId());
        return paymentResponseDTO;
    }


    @Transactional
    public PaymentResponseDTO updatePayment(Long orderId, PaymentDTO paymentDTO) {
        Order order = orderHelper.getOrderById(orderId);
        Payment payment = paymentHelper.getPaymentById(order.getPayment().getId());
        paymentHelper.validate(payment);
        payment.setType(paymentDTO.getType());

        PaymentResponseDTO paymentResponse = payByPayU(order, payment);
        paymentRepository.save(payment);
        return paymentResponse;
    }

    private void updateExistingPayment(Payment payment, PaymentType type) {
        payment.setType(type);
        paymentRepository.save(payment);
    }

    private void createNewPayment(Order order, PaymentType paymentType) {
        Payment payment = new Payment(order, paymentType);
        paymentRepository.save(payment);
    }

    private void checkIfExist(Long orderId, PaymentType paymentType) {
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


    private PaymentResponseDTO payByPayU(Order order, Payment payment) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        if (PaymentType.PAYU.equals(payment.getType())) {
            PayuDTO payuDTO = payuMapper.map(order);
            PayuOrderResponseDTO payuOrderResponseDTO = payUService.createOrderPayu(payuDTO);
            paymentResponseDTO.setRedirectUri(payuOrderResponseDTO.getRedirectUri());
            payment.setPayuOrderId(payuOrderResponseDTO.getOrderId());
        }
        return paymentResponseDTO;
    }

}
