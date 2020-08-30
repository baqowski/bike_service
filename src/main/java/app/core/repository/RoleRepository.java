package app.core.repository;

import app.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
