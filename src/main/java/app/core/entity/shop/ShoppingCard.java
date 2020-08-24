package app.core.entity.shop;

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
@NoArgsConstructor
public class ShoppingCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @OneToMany(mappedBy = "shoppingCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCardProduct> products;

    @OneToOne
    @MapsId
    private User user;

    public ShoppingCard(User user) {
        this.user = user;
    }

    /* public void updateAmount() {
        setAmount(this.products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public ShoppingCard() {
        this.products = new ArrayList<>();
    }*/
}
