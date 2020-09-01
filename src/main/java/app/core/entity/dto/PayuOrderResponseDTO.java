package app.core.entity.dto;

import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
public class PayuOrderResponseDTO {

    private String redirectUri;

    private String orderId;
}
