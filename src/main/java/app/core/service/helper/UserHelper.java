package app.core.service.helper;

import app.core.entity.User;
import app.core.exception.UserNotFoundException;
import app.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public User getUserFormSecurityContext() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Brak uzytkownika o takiej nazwie: " + username));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Brak uzytkownika o takiej nazwie: " + username));
    }


}
