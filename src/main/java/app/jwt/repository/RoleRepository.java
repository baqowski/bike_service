package app.jwt.repository;

import app.jwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
