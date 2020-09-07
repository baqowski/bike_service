package app.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Karol Bąk
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(exclude = "deliveryOrder")
public class DeliveryAddress extends AddressSuperclass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "deliveryAddress")
    /*@JsonBackReference*/
    private DeliveryOrder deliveryOrder;




}
