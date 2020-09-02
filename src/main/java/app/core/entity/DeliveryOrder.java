package app.core.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Entity
@Data
@ToString(exclude = {"delivery", "deliveryAddress"})
public class DeliveryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "deliveryOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ManyToOne
    private Delivery delivery;

    @OneToOne
    private DeliveryAddress deliveryAddress;


}
