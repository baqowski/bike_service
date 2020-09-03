package app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol Bąk
 */
@Entity
@Data
/*@AllArgsConstructor
@NoArgsConstructor*/
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String categoryName;

    @OneToOne
    private ProductCategory embedded;

    @OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
    private List<Product> products;

    public void setProducts(List<Product> products) {
        if (products != null) {
            products.forEach(product -> product.setProductCategory(this));
        }
        this.products = products;
    }

    public ProductCategory() {
        this.products = new ArrayList<>();
    }
}
