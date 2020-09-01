package app.core.entity;

import app.core.entity.type.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Karol Bąk
 */
@Entity
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String payuId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @OneToOne
    private Order order;

}
