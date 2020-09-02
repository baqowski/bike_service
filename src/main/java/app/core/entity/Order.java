package app.core.entity;

import app.core.entity.type.OrderStatus;
import app.core.entity.type.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Karol Bąk
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_order")
@ToString(exclude = "delivery")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private BigDecimal amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties
    private List<OrderProduct> products;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToOne
    private Delivery delivery;

    @ManyToOne
    @JsonIgnoreProperties
    private User user;
}
