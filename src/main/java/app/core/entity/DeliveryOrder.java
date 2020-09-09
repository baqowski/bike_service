package app.core.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Entity
@Data
@ToString(exclude = {"delivery", "deliveryAddress"})
@NoArgsConstructor
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "deliveryOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders;

    @ManyToOne
    @JsonManagedReference
    /*@JsonBackReference*/
    private Delivery delivery;

    @OneToOne
    /*@JsonManagedReference*/
    private DeliveryAddress deliveryAddress;

    public DeliveryOrder(Delivery delivery, DeliveryAddress deliveryAddress) {
        this.delivery = delivery;
        this.deliveryAddress = deliveryAddress;
    }
}
