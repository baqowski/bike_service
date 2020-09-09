package app.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@ToString(exclude = {"product"})
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JsonBackReference
    private Product product;

    public OrderProduct(Product product, Order order, Integer quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }
}
