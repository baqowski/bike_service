package app.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
@AllArgsConstructor
public class PayUAutBodyDTO {

    private String grant_type;
    private String client_id;
    private String client_secret;
}
