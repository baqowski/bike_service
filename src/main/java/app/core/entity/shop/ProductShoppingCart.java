package app.core.entity.shop;

import com.sun.istack.NotNull;
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
@ToString(exclude = {"shoppingCart", "product"})
public class ProductShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private ShoppingCart shoppingCart;

    private Integer count;

    public ProductShoppingCart(Product product, ShoppingCart shoppingCart, Integer count) {
        this.product = product;
        this.shoppingCart = shoppingCart;
        this.count = count;
    }
}
