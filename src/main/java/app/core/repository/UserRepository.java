package app.core.repository;

import app.core.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

   User findByUsername(String username);

   Optional<User> findByUuid(String uuid);
}
