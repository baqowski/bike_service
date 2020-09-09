package app.core.entity;

import app.core.entity.type.OrderServiceType;
import app.core.entity.type.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString(exclude = {"deliveryOrder", "payment", "products"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private OrderServiceType orderServiceType;

    private BigDecimal amount;

    @OneToMany(mappedBy = "order" /*cascade = CascadeType.ALL, orphanRemoval = true*/)
    @JsonManagedReference
    private List<OrderProduct> products;

    @OneToOne(mappedBy = "order")
    @JsonBackReference
    private Payment payment;

    @ManyToOne
    /*@JsonManagedReference*/
    @JsonBackReference
    private DeliveryOrder deliveryOrder;

    @ManyToOne
    /*@JsonManagedReference*/
    private User user;
}
