package app.core.service;

import app.jwt.dto.PayUResponseAuthDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @author Karol Bąk
 */
@Service
@PropertySource("classpath:payu.properties")
public class PayUService {


    @Value("${payu.sandbox.grantType}")
    private String grantType;

    @Value("${payu.sandbox.clientId}")
    private String clientId;

    @Value("${payu.sandbox.clientSecret}")
    private String clientSecret;

    @Value("${payu.sandbox.authorizeUrl}")
    private String authorizeUrl;

    public PayUResponseAuthDTO authorize() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type="+grantType+"&client_id="+clientId+"&client_secret="+clientSecret;
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(authorizeUrl, request, PayUResponseAuthDTO.class);
    }

    public void createOrder() {


    }
}
