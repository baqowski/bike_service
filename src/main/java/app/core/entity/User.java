package app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * @author Karol Bąk
 */

@Entity
@Data
@AllArgsConstructor
@ToString(exclude = {"role", "orders"})
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private String uuid;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private Boolean isLogged;

    @ManyToOne
    /*@ToString.Exclude*/
    private Role role;

/*    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserProduct> userProducts;*/

  /*  @ManyToMany
    @JoinTable(
            name = "user_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<DeliveryAddress> deliveryAddresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(role);
        /*userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().getName())));*/
        return authorities;
    }

    public User() {
        this.uuid = String.valueOf(UUID.randomUUID());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
