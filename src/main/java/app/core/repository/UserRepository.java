package app.core.repository;

import app.core.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

   Optional<User> findByUsername(String username);

   Optional<User> findByUuid(String uuid);
}
