package app.core.service;

import app.bike_app.AbstractIntegrationTest;
import app.core.entity.User;
import app.core.entity.repository.RoleRepository;
import app.core.entity.repository.UserRepository;
import app.core.service.helper.UserHelper;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.dto.UserDTO;
import app.jwt.service.AuthorizationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AuthorizationService authorizationService;

    @Autowired
    private  UserService userService;

    @Autowired
    private UserHelper userHelper;

    @BeforeEach
    void setUp() {
        /* createUser();*/
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Transactional
    @Test
    public void shouldCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test-username");
        userDTO.setPassword("test-password");
        userDTO.setEmail("test-email@test.pl");
        userService.createUser(userDTO);

        /*toDo*/
        User user = userHelper.getUserByUsername(userDTO.getUsername());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userDTO.getUsername(), user.getUsername());

       /* ShoppingCart shoppingCart = shoppingCardRepository.findByUser_UsernameAndIsActiveTrue(user.getUsername());*/

        /*Assertions.assertNotNull(shoppingCart);*/

    }

    @Transactional
    @Test
    public void shouldLoginUser() {
        RequestJWT requestJWT = new RequestJWT("username", "password");
        /*userService.createUser(new UserDTO(requestJWT.getUsername(), requestJWT.getPassword(), "email"));*/

        ResponseJWT responseJWT = authorizationService.login(requestJWT);

       /* Assertions.assertEquals(responseJWT.getUsername(), SecurityContextHolder.getContext().getAuthentication().getName());*/
    }
}
