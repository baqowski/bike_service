package app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Karol Bąk
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "productCategory")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private BigDecimal price;

    private String imageUrl;

    private String color;

    private String producer;

    @ManyToOne
    @JsonIgnore
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderProduct> orders;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
