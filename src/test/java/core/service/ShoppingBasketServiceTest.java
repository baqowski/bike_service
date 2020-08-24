package core.service;

import app.bike_app.AbstractIntegrationTest;
import app.core.repository.ProductRepository;
import app.jwt.entity.Role;
import app.jwt.entity.User;
import app.jwt.entity.UserRole;
import app.jwt.repository.RoleRepository;
import app.jwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;


/**
 * @author Karol Bąk
 */
class ShoppingBasketServiceTest extends AbstractIntegrationTest {



    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userRepository.save(user);
        System.out.println("test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("test");
    }

    @Test
    public void test(){

    }

    private void user() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role());

        user.setUserRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }

    private Role role() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

   /* private ShoppingCard ShoppingCard() {
        ShoppingCard shoppingBasket = new ShoppingCard();
       return null;
    }*/

   /* private Product product(String name, BigDecimal price) {
        Product product = new Product(name, price);
        return productRepository.save(product);
    }*/
}
