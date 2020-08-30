package app.core.entity;

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
@ToString(exclude = {"order", "userProduct"})
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private UserProduct userProduct;

    public OrderProduct(UserProduct userProduct, Order order) {
        this.userProduct = userProduct;
        this.order = order;
    }
}
