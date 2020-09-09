package app.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private BigDecimal loanDayPrice;

    private LocalDateTime loanStartDate;

    private LocalDateTime loanEndDate;

    private String imageUrl;

    private String description;

    private String color;

    private String producer;

    @ManyToOne
    @JsonIgnore
    @JsonManagedReference
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @JsonBackReference
    private List<OrderProduct> orders;

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
