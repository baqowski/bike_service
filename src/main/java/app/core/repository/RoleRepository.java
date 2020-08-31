package app.core.repository;

import app.core.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Karol Bąk
 */

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

}
