package app.core.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karol Bąk
 */
@Data
@AllArgsConstructor
@Builder
public class DeliveryAddressDTO {

    private String street;

    private String houseNumber;

    private String postalCode;

    private String city;

    private String country;

    public DeliveryAddressDTO() {
    }
}
