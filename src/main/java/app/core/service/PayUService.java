package app.core.service;

import app.core.entity.dto.PayuDTO;
import app.core.entity.dto.PayuOrderResponseDTO;
import app.core.entity.dto.PayuStatusDTO;
import app.jwt.dto.PayUResponseAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @author Karol Bąk
 */
@Service
@PropertySource("classpath:payu.properties")
@RequiredArgsConstructor
public class PayUService {

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

    public PayUResponseAuthDTO authorizeWithPayU() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type=" + grantType + "&client_id=" + clientId + "&client_secret=" + clientSecret;
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

    public ResponseEntity<PayuStatusDTO> checkOrderStatus(String orderId) {
        PayUResponseAuthDTO payUResponseAuthDTO = authorizeWithPayU();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("authorization", payUResponseAuthDTO.getToken_type() + " " + payUResponseAuthDTO.getAccess_token());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(hostUrl + "/api/v2_1/orders/"+ orderId, HttpMethod.GET, requestEntity, PayuStatusDTO.class);
    }
}
