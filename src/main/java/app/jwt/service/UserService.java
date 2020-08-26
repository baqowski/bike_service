package app.jwt.service;


import app.core.repository.ProductShoppingCartRepository;
import app.core.repository.ShoppingCardRepository;
import app.core.service.shop.ShoppingCartService;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.dto.UserDTO;
import app.jwt.entity.Role;
import app.jwt.entity.User;
import app.jwt.repository.RoleRepository;
import app.jwt.repository.UserRepository;
import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

import static app.jwt.SecurityConstants.EXPIRATION_TIME;
import static app.jwt.SecurityConstants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ShoppingCardRepository shoppingCardRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProductShoppingCartRepository productShoppingCartRepository;


    private void changeLoginStatus() {
       /* User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());*/
        /*user.setIsLogged(Boolean.TRUE);
        userRepository.save(user);*/
    }

    public ResponseJWT login(RequestJWT requestJWT) throws AuthenticationException, UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestJWT.getUsername(), requestJWT.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        changeLoginStatus();

        return new ResponseJWT(requestJWT.getUsername(), createTokenJWT(requestJWT.getUsername()), getUserRole(requestJWT.getUsername()), LocalDateTime.now().plusNanos(2000000));

    }

    private String createTokenJWT(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }


    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow( ()-> new UsernameNotFoundException("Brak uzytkownika o takiej nazwie: " + username));
        user.setIsLogged(false);
        userRepository.save(user);
        log.info("Log out user: " + authentication.getName());
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }

    public void createUser(UserDTO userDTO) {
        // Creating user's account
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(roleRepository.findByName("ROLE_USER"));
        /*user.setUserRoles(Collections.singletonList(userRole));*/
        shoppingCartService.createNewUserShoppingCart(user);
    }

    private String getUserRole(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow( ()-> new UsernameNotFoundException("Brak uzytkownika o takiej nazwie: " + username))
                .getRole().getName();
    }
}
