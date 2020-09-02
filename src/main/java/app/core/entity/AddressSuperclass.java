package app.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

/**
 * @author Karol Bąk
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AddressSuperclass {

    private String street;

    private String houseNumber;

    private String postalCode;

    private String city;

    private String country;
}
