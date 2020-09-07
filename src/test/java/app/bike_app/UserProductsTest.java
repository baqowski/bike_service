package app.bike_app;


import app.core.entity.Product;
import app.core.entity.Role;
import app.core.entity.User;
import app.core.entity.repository.ProductRepository;
import app.core.entity.repository.RoleRepository;
import app.core.entity.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author Karol Bąk
 */

/*@RunWith(SpringRunner.class)*/
public class UserProductsTest extends AbstractIntegrationTest {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

/*    @Autowired
    private UserProductRepository userProductRepository;*/

    private User user;

    @Transactional
    @BeforeEach
    void setUp() {
        user = user();
    }

    @AfterEach
    void tearDown() {
        /*userProductRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        productRepository.deleteAll();
        System.out.println("test");*/
    }

    @Transactional
    @Test
    public void userProductTest(){
        //given
/*        user.setUserProducts(userProducts());

        //then
        assertEquals(2, user.getUserProducts().size());*/

    }

    private User user() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(role("ROLE_USER"));
        return userRepository.save(user);
    }

    private Role role(String roleName) {
        Role role = new Role();
        role.setId(1L);
        role.setName(roleName);
        return roleRepository.save(role);
    }

    private Product product(String name, BigDecimal price) {
        return productRepository.save(new Product(name, price));
    }

  /*  private List<UserProduct> userProducts() {
        return List.of(
                userProduct(1, user, product("Prduct1", BigDecimal.valueOf(19.99))),
                userProduct(2, user, product("Prduct2", BigDecimal.valueOf(9.99)))
        );

    }
    private UserProduct userProduct(Integer quantity, User user, Product product) {
        return userProductRepository.save(new UserProduct(quantity, user, product));
    }*/
}
