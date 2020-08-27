package app.core.entity.shop;

import app.core.entity.Order;
import app.jwt.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Karol Bąk
 */

@Entity
@Data
@ToString(exclude = {"user"})
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductShoppingCart> productShoppingCarts;

    @OneToMany(mappedBy = "shoppingCart")
    private List<Order> orders;

    @ManyToOne
    private User user;

    private Boolean isActive;

    public ShoppingCart() {
        isActive = Boolean.TRUE;
    }
}
