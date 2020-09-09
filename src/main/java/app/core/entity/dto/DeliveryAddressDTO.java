package app.core.entity.dto;

import lombok.Data;

/**
 * @author Karol Bąk
 */
@Data
public class DeliveryAddressDTO {

    private String street;

    private String houseNumber;

    private String postalCode;

    private String city;

    private String country;
}
