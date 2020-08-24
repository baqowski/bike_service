package app.jwt.controller;

import app.jwt.dto.RequestJWT;
import app.jwt.dto.UserDTO;
import app.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
        return ResponseEntity.ok(userService.login(requestJWT));
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
