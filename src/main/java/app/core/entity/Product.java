package app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderProduct> orders;

    /*@ManyToMany(mappedBy = "products")
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;*/

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
