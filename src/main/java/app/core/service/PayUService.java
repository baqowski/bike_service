package app.core.service;

import app.core.entity.Order;
import app.core.entity.OrderProduct;
import app.core.entity.User;
import app.core.entity.dto.*;
import app.core.repository.OrderProductRepository;
import app.core.repository.OrderRepository;
import app.core.service.helper.OrderHelper;
import app.core.service.helper.UserHelper;
import app.core.service.mapper.PayuDtoMapper;
import app.jwt.dto.PayUResponseAuthDTO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Karol Bąk
 */
@Service
@PropertySource("classpath:payu.properties")
@RequiredArgsConstructor
public class PayUService implements PayuDtoMapper {

    @Value("${payu.sandbox.grantType}")
    private String grantType;

    @Value("${payu.sandbox.clientId}")
    private String clientId;

    @Value("${payu.sandbox.clientSecret}")
    private String clientSecret;

    @Value("${payu.sandbox.authorizeUrl}")
    private String authorizeUrl;

    @Value("${payu.sandbox.hostUrl}")
    private String hostUrl;

    private final UserHelper userHelper;
    private final OrderHelper orderHelper;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;



    public PayUResponseAuthDTO authorizeWithPayU() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type="+grantType+"&client_id="+clientId+"&client_secret="+clientSecret;
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(authorizeUrl, request, PayUResponseAuthDTO.class);
    }

    public PayuOrderResponseDTO createOrderPayu(PayuDTO payuDTO) {
        PayUResponseAuthDTO payUResponseAuthDTO = authorizeWithPayU();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("authorization", payUResponseAuthDTO.getToken_type() + " " + payUResponseAuthDTO.getAccess_token());
        HttpEntity<PayuDTO> request = new HttpEntity<>(payuDTO, headers);
        return restTemplate.postForObject(hostUrl + "/api/v2_1/orders", request, PayuOrderResponseDTO.class);
    }

    @Override
    public PayuDTO map(Long orderId) {
        User user = userHelper.getUserFormSecurityContext();
        PayuDTO payuDTO = new PayuDTO();

        ClientDTO clientDTO = map(user);
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder_Id(orderId);
        List<PayuProductDTO> productDTOList = map(orderProducts);

        payuDTO.setBuyer(clientDTO);
        payuDTO.setProducts(productDTOList);
        payuDTO.setMerchantPosId(clientId);

        Order order = orderHelper.getOrderById(orderId);
        payuDTO.setTotalAmount(mapTotalCostToPayuFormat(orderHelper.calculateOrderSummaryPrice(order)));
        payuDTO.setDescription("Bike service system");
        payuDTO.setCurrencyCode("PLN");
        payuDTO.setCustomerIp("127.0.0.1");
        payuDTO.setNotifyUrl("https://your.eshop.com/notify");

        return payuDTO;
    }

    @Override
    public ClientDTO map(User user) {
        return ClientDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    @Override
    public List<PayuProductDTO> map(List<OrderProduct> orderProducts) {
        return orderProducts.stream().map(this::toPayuProductDTO).collect(Collectors.toList());
    }

    private PayuProductDTO toPayuProductDTO(OrderProduct orderProduct) {
        return PayuProductDTO.builder()
                .name(orderProduct.getProduct().getName())
                .quantity(orderProduct.getQuantity().toString())
                .unitPrice(mapTotalCostToPayuFormat(orderProduct.getProduct().getPrice()))
                .build();
    }

    private String mapTotalCostToPayuFormat(BigDecimal bigDecimal) {
        return bigDecimal.toString().replace(".", "");
    }
}
