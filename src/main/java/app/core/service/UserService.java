package app.core.service;

import app.core.entity.Order;
import app.core.entity.User;
import app.core.exception.ForbiddenException;
import app.core.repository.OrderRepository;
import app.core.repository.RoleRepository;
import app.core.repository.UserRepository;
import app.core.service.helper.OrderHelper;
import app.jwt.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderRepository orderRepository;
    private final OrderHelper orderHelper;

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
       /* User user = userRepository.findByUsername(username)*//*.orElseThrow( ()-> new UsernameNotFoundException("Brak uzytkownika o takiej nazwie: " + username))*//*;*/
    /*    user.setIsLogged(false);
        userRepository.save(user);*/
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

    public Long getOrderId(String uuid, Long orderId) {
        User user = orderHelper.getUserByUuid(uuid);
        List<Order> userOrders = orderRepository.getAllByUser_Id(user.getId());
        Order order = orderHelper.getOrderById(orderId);
        if ("ROLE_USER".equals(user.getRole().getName()) && !userOrders.isEmpty() && !userOrders.contains(order)) {
            throw new ForbiddenException("Brak uprawnnień");
        }
        return orderHelper.getOrderById(orderId).getId();
    }


}
