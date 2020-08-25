package app.jwt.service;


import app.core.repository.ProductShoppingCartRepository;
import app.core.repository.ShoppingCardRepository;
import app.core.service.shop.ShoppingCartService;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.dto.UserDTO;
import app.jwt.entity.Role;
import app.jwt.entity.User;
import app.jwt.entity.UserRole;
import app.jwt.repository.RoleRepository;
import app.jwt.repository.UserRepository;
import app.jwt.repository.UserRoleRepository;
import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static app.jwt.SecurityConstants.EXPIRATION_TIME;
import static app.jwt.SecurityConstants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ShoppingCardRepository shoppingCardRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProductShoppingCartRepository productShoppingCartRepository;


    private void changeLoginStatus() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setIsLogged(Boolean.TRUE);
        /*List<UserRole> userRoles = userRoleRepository.findAllByUser(user);
        user.setUserRoles(userRoles);*/
        userRepository.save(user);
    }

    public ResponseJWT login(RequestJWT requestJWT) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestJWT.getUsername(), requestJWT.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        changeLoginStatus();

        return new ResponseJWT(requestJWT.getUsername(), createTokenJWT(requestJWT.getUsername()), LocalDateTime.now().plusNanos(2000000));

    }

    private String createTokenJWT(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public List<UserRole> getUserRoles(Long userId)
    {
        return userRoleRepository.findAllByUser_Id(userId);
    }


    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        user.setIsLogged(false);
        userRepository.save(user);
        log.info("Log out user: " + authentication.getName());
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }

    public void createUser(UserDTO userDTO) {
        // Creating user's account
        Role role = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        user.setUserRoles(Collections.singletonList(userRole));
        shoppingCartService.createNewUserShoppingCart(user);
        log.info(user.getUserShoppingCarts().toString());
    }
}
