package app.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Karol Bąk
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class DeliveryAddress extends AddressSuperclass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "deliveryAddress")
    private DeliveryOrder deliveryOrder;




}
