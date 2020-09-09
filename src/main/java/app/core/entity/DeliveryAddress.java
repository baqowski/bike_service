package app.core.entity;

import app.core.entity.dto.DeliveryAddressDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Karol Bąk
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "deliveryOrder")
public class DeliveryAddress extends AddressSuperclass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "deliveryAddress")
    @JsonBackReference
    private DeliveryOrder deliveryOrder;

    public DeliveryAddress(DeliveryAddressDTO deliveryAddressDTO) {
        this.setStreet(deliveryAddressDTO.getStreet());
        this.setHouseNumber(deliveryAddressDTO.getHouseNumber());
        this.setPostalCode(deliveryAddressDTO.getPostalCode());
        this.setCity(deliveryAddressDTO.getCity());
        this.setCountry(deliveryAddressDTO.getCountry());
    }
}
