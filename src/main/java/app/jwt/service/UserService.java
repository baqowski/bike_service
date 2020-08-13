package app.jwt.service;

import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.entity.User;
import app.jwt.entity.UserRole;
import app.jwt.repository.UserRepository;
import app.jwt.repository.UserRoleRepository;
import com.auth0.jwt.JWT;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static app.jwt.SecurityConstants.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;


    private void changeLoginStatus(Boolean status) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setIsLogged(status);
        userRepository.save(user);
    }

    public ResponseJWT login(RequestJWT requestJWT) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestJWT.getUsername(), requestJWT.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        changeLoginStatus(true);

        return new ResponseJWT(requestJWT.getUsername(), createTokenJWT(requestJWT.getUsername()), LocalDateTime.now().plusHours(EXPIRATION_TIME));

    }

    private String createTokenJWT(String username) {
        return  JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public List<UserRole> getUserRoles (Long userId) {
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
   /* public User createUser(JwtRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        UserRole userRole = new UserRole(newUser, roleRepository.findByName("ROLE_USER"));
        List<UserRole> userRoleSet = new ArrayList<>();
        userRoleSet.add(userRole);
        newUser.setUserRoles(userRoleSet);
        return userRepository.save(newUser);
    }*/
}
