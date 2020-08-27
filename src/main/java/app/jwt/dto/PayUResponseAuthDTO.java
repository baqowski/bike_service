package app.jwt.dto;

import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
public class PayUResponseAuthDTO {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String grant_type;
}
