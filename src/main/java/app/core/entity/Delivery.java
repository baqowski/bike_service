package app.core.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Data
@Entity
@ToString(exclude = "deliveryAddress")
public class Delivery {

    @Id
    private Long id;

    private String type;

    private BigDecimal cost;

    @OneToOne(mappedBy = "delivery")
    private DeliveryAddress deliveryAddress;
}
