package app.core.service;

import app.bike_app.BikeAppApplicationTests;
import app.core.entity.Product;
import app.core.entity.ShoppingBasket;
import app.core.repository.ProductRepository;
import app.core.repository.ShoppingBasketRepository;
import app.jwt.entity.Role;
import app.jwt.entity.User;
import app.jwt.entity.UserRole;
import app.jwt.repository.RoleRepository;
import app.jwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author Karol Bąk
 */
class ShoppingBasketServiceTest extends BikeAppApplicationTests {

    @Autowired
    private ShoppingBasketService shoppingBasketService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;

    @Autowired
    private ProductRepository productRepository;

    private ShoppingBasket shoppingBasket;
    @BeforeEach
    void setUp() {
        System.out.println("test");
        shoppingBasket = shoppingBasket();
    }

    @AfterEach
    void tearDown() {
        System.out.println("test");
        /*roleRepository.deleteAll();
        userRepository.deleteAll();
        shoppingBasketRepository.deleteAll();*/
    }

    @Transactional
    @Test
    public void shouldAddProductToBasket() {
        shoppingBasketService.addProductToShoppingBasket(1L);
        System.out.println("test");
    }

    @Test
    public void test(){
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setProducts(Arrays.asList(new Product(BigDecimal.valueOf(9.99)), new Product(BigDecimal.valueOf(19.99))));
        shoppingBasket.updateAmount();
        Assertions.assertEquals(BigDecimal.valueOf(29.98), shoppingBasket.getAmount());
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

    private ShoppingBasket shoppingBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.getProducts().add(product("Rama", BigDecimal.valueOf(109.99)));
        shoppingBasket.getProducts().add(product("Koło", BigDecimal.valueOf(39.99)));
        return shoppingBasketRepository.save(shoppingBasket);
    }

    private Product product(String name, BigDecimal price) {
        Product product = new Product(name, price);
        return productRepository.save(product);

    }
}
