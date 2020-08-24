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
@ToString(exclude = {"shoppingCard", "product"})
public class ShoppingCardProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    @NotNull
    private ShoppingCard shoppingCard;

    private Integer count;

    public ShoppingCardProduct(Product product, ShoppingCard shoppingCard, Integer count) {
        this.product = product;
        this.shoppingCard = shoppingCard;
        this.count = count;
    }
}
