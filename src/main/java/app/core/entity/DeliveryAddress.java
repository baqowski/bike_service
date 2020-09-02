package app.core.entity;

import lombok.*;

import javax.persistence.*;

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
