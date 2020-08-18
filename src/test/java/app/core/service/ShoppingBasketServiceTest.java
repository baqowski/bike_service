package app.core.service;

import app.bike_app.BikeAppApplicationTests;
import app.core.entity.ShoppingBasket;
import app.core.entity.Product;
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
import org.springframework.util.Assert;

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

    @BeforeEach
    void setUp() {
        System.out.println("test");
        /*user();*/
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
    public void shouldCreateUserShoppingBasket() {
        shoppingBasketService.createUserShoppingBasket("username");
        List<ShoppingBasket> shoppingBasketList = shoppingBasketRepository.findAll();
        System.out.println(shoppingBasketList);
    }

    @Transactional
    @Test
    public void shouldAddProductToBasket() {
        Product product = new Product();
        product.setName("Rower");
        ShoppingBasket shoppingBasket = shoppingBasketRepository.findByUser_Username("username");
        product.setBasket(shoppingBasket);
        productRepository.save(product);
        /*shoppingBasket.getProducts().add(product);
        shoppingBasketRepository.save(shoppingBasket);*/
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
}
