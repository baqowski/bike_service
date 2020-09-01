package app.core.entity;

import app.core.entity.type.DeliveryType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Data
@Entity
public class Delivery {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private BigDecimal cost;

    @OneToOne
    private Order order;

    @OneToOne(mappedBy = "delivery")
    private DeliveryAddress deliveryAddress;


}
