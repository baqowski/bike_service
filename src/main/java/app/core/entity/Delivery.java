package app.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Data
@Entity
public class Delivery {

    @Id
    private Long id;

    private String type;

    private BigDecimal cost;

    @OneToMany(mappedBy = "delivery")
    /*@JsonBackReference*/
    private List<DeliveryOrder> deliveryOrders;




}
