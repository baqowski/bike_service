package app.jwt.service;

import app.bike_app.AbstractIntegrationTest;
import app.core.entity.shop.ShoppingCard;
import app.core.repository.ShoppingCardRepository;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.dto.UserDTO;
import app.jwt.entity.User;
import app.jwt.repository.RoleRepository;
import app.jwt.repository.UserRepository;
import app.jwt.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

/**
 * @author Karol Bąk
 */
class UserServiceTest extends AbstractIntegrationTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCardRepository shoppingCardRepository;

    @BeforeEach
    void setUp() {
        /* createUser();*/
    }

    @AfterEach
    void tearDown() {
        shoppingCardRepository.deleteAll();
        userRoleRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Transactional
    @Test
    public void shouldCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test-username");
        userDTO.setPassword("test-password");
        userDTO.setEmail("test-email@test.pl");userService.createUser(userDTO);
        User user = userRepository.findByUsername("test-username");

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userDTO.getUsername(), user.getUsername());

        ShoppingCard shoppingCard = shoppingCardRepository.findByUser_Username(user.getUsername());

        Assertions.assertNotNull(shoppingCard);

    }

    @Transactional
    @Test
    public void shouldLoginUser() {
        RequestJWT requestJWT = new RequestJWT("username", "password");
        userService.createUser(new UserDTO(requestJWT.getUsername(), requestJWT.getPassword(), "email"));

        ResponseJWT responseJWT = userService.login(requestJWT);

        Assertions.assertEquals(responseJWT.getUsername(), SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
