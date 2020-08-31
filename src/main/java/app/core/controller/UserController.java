package app.core.controller;

import app.core.entity.User;
import app.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
/**
 * @author Karol Bąk
 */

@RequestMapping("/api/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        userService.logout();
    }

    @GetMapping("/{username}/test")
    public User getUserByUsername(@PathVariable String username){
       return userService.getUserByUsername(username);


    }

    @GetMapping("/{/userId}/orders")
    public void userOrders(@PathVariable Long userId){
    }
}
