package app.core.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author Karol Bąk
 */

@Entity
@Data
public class DeliveryAddress extends AddressSuperclass {

    @Id
    private Long id;

    @OneToOne
    private Delivery delivery;
}
