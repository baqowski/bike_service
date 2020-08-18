package app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */
@Entity
@Data
@ToString(exclude = "basket")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    @JsonIgnore
    private ShoppingBasket basket;

    public Product(BigDecimal price) {
        this.price = price;
    }
}
