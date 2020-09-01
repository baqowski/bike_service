package app.core.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Karol Bąk
 */
@Data
@NoArgsConstructor
public class PayuDTO {

    @Value("${payu.sandbox.notifyUrl}")
    private String notifyUrl;

    private String customerIp;

    @Value("${payu.sandbox.clientId}")
    private String merchantPosId;

    private String description;

    private String currencyCode;

    private String totalAmount;

    private String extOrderId;

    private ClientDTO buyer;

    private List<PayuProductDTO> products;


}
