package app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@ToString(exclude = "shoppingBasketList")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private BigDecimal price;

    /*@ManyToOne
    @JsonIgnore
    private ShoppingBasket basket;*/

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

    public Product(BigDecimal price) {
        this.price = price;
    }

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.shoppingBasketList = new ArrayList<>();
    }
}
