package app.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

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

    @OneToOne(mappedBy = "delivery")
    private Order order;


}
