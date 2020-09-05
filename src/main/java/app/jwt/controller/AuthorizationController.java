package app.jwt.controller;

import app.core.service.UserService;
import app.jwt.dto.RequestJWT;
import app.jwt.dto.ResponseJWT;
import app.jwt.dto.UserDTO;
import app.jwt.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final AuthorizationService authorizationService;


    @PostMapping("/login")
    public ResponseJWT login(@RequestBody RequestJWT requestJWT) throws AuthenticationException {
        log.info("Attempt to login user: " + requestJWT.getUsername());
        return authorizationService.login(requestJWT);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDTO userDTO) {
        log.info("Creating user account " + userDTO.getUsername());
        userService.createUser(userDTO);
    }

   /* @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        userService.logout();
    }*/
}
