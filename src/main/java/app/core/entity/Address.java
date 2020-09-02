package app.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Karol Bąk
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Address extends AddressSuperclass {

    @Id
    private Long id;

    @OneToOne
    private Delivery delivery;

    @ManyToOne
    private User user;
}
