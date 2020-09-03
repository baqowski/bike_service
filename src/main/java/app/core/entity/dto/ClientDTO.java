package app.core.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
@Builder
@AllArgsConstructor
public class ClientDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
