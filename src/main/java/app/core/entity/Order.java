package app.core.entity;

import app.core.entity.shop.ShoppingCart;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * @author Karol Bąk
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_shopping_cart")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String amount;

    @ManyToOne
    private ShoppingCart shoppingCart;
}
