package app.core.entity;

import app.jwt.entity.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol Bąk
 */

@Entity
@Data
@ToString(exclude = "user")
public class ShoppingBasket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    /* @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Product> products;*/
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "shopping_basket_product",
            joinColumns = @JoinColumn(name = "shopping_basket_it"),
            inverseJoinColumns = @JoinColumn (name = "product_id")
    )
    private List<Product> products;

    @OneToOne
    private User user;

    public void updateAmount() {
        setAmount(this.products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public ShoppingBasket() {
        this.products = new ArrayList<>();
    }
}
