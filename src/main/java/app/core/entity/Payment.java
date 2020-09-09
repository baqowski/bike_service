package app.core.entity;

import app.core.entity.type.PaymentStatus;
import app.core.entity.type.PaymentType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Karol Bąk
 */
@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "order")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String payuOrderId;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

/*    @JsonIgnore*/
    @OneToOne
    @JsonManagedReference
    private Order order;

    public Payment(Order order, PaymentType paymentType) {
        this.type = paymentType;
        this.order = order;
    }

    public Payment(PaymentType paymentType) {
        this.type = paymentType;
    }
}
