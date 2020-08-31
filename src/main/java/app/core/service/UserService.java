package app.core.service;

import app.core.entity.User;
import app.core.repository.OrderRepository;
import app.core.repository.RoleRepository;
import app.core.repository.UserRepository;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.dto.UserDTO;
import com.auth0.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final OrderRepository orderRepository;
    private final OrderService orderService;


    private void changeLoginStatus() {
       /* User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());*/
        /*user.setIsLogged(Boolean.TRUE);
        userRepository.save(user);*/
    }

    public ResponseJWT login(RequestJWT requestJWT) throws AuthenticationException{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestJWT.getUsername(), requestJWT.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseJWT(requestJWT.getUsername(), createTokenJWT(requestJWT.getUsername()), LocalDateTime.now().plusNanos(2000000));

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
        User user = userRepository.findByUsername(username)/*.orElseThrow( ()-> new UsernameNotFoundException("Brak uzytkownika o takiej nazwie: " + username))*/;
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
        userRepository.save(user);
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
               /* .orElseThrow( ()-> new UsernameNotFoundException("Brak uzytkownika o takiej nazwie: " + username));*/
    }



}
