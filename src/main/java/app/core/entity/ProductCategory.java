package app.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"embedded", "products"})
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String categoryName;

    @OneToOne
    @JoinColumn(name = "product_category_id")
    @JsonIgnore
    private ProductCategory embedded;

    @OneToMany(mappedBy = "productCategory")
    @JsonIgnore
    private List<Product> products;
}
