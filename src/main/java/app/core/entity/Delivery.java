package app.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Data
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private BigDecimal cost;

    @OneToMany(mappedBy = "delivery")
    @JsonBackReference
    private List<DeliveryOrder> deliveryOrders;




}
