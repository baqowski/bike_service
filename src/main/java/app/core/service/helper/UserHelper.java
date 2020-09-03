package app.core.service.helper;

import app.core.entity.User;
import app.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Karol Bąk
 */
@Service
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public User getUserFormSecurityContext() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
