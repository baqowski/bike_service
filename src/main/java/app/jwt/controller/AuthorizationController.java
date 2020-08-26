package app.jwt.controller;

import app.jwt.dto.RequestJWT;
import app.jwt.dto.UserDTO;
import app.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * @author Karol Bąk
 */
@RestController
@RequestMapping("/authorization")
@Slf4j
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestJWT requestJWT) throws AuthenticationException {
        log.info("Signing in user: " + requestJWT.getUsername());
        try {
            return ResponseEntity.ok(userService.login(requestJWT));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            return ResponseEntity.unprocessableEntity().body("Błędny login lub hasło");
        }

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDTO userDTO) {
        log.info("Creating user account " + userDTO.getUsername());
        userService.createUser(userDTO);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        userService.logout();
    }
}
