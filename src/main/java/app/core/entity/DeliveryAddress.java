package app.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Karol Bąk
 */

@Entity
@Data
public class DeliveryAddress extends AddressSuperclass {

    @Id
    private Long id;

    @ManyToOne
    private User user;
}
