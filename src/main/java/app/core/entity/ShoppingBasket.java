package app.core.entity;
import app.core.entity.Product;
import app.jwt.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karol Bąk
 */

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "user")
public class ShoppingBasket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @OneToOne
    private User user;

    public void updateAmount() {
        setAmount(this.products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
