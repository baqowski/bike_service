package app.core.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;

/**
 * @author Karol Bąk
 */
@MappedSuperclass
@Data
public abstract class AddressSuperclass {

    private String street;

    private Integer houseNumber;

    private String postalCode;

    private String city;

    private String country;
}
