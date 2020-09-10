package app.core.controller;

import app.core.entity.Order;
import app.core.entity.User;
import app.core.service.UserService;
import app.core.service.helper.UserHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final UserHelper userHelper;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        userService.logout();
    }

    @GetMapping("/current")
    public User getUserByUuid() {
        return userHelper.getUserFormSecurityContext();
    }


    @GetMapping("/{uuid}/orders/{orderId}")
    public Order getUserOrderId(@PathVariable String uuid, @PathVariable Long orderId) throws JsonProcessingException {
        return userService.findUserOrder(uuid, orderId);
    }
}
