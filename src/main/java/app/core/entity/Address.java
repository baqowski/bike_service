package app.core.entity;

import lombok.*;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.persistence.OneToOne;

/**
 * @author Karol Bąk
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress extends AddressSuperclass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Delivery delivery;


}
