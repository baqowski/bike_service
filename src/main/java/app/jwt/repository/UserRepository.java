package app.jwt.repository;

import app.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUsername(String username);
}
