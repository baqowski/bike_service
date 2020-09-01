package app.core.controller;

import app.core.entity.Order;
import app.core.entity.User;
import app.core.repository.UserRepository;
import app.core.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{uuid}/orders/{orderId}")
    public void userOrders(@PathVariable String uuid, @PathVariable Long orderId){
        userService.checkAccessUserToOrder(uuid, orderId);
    }
}
