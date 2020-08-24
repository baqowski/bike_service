package app.jwt.repository;


import app.jwt.entity.User;
import app.jwt.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    List<UserRole> findAllByUser_Id(Long userId);

    List<UserRole> findAllByUser(User user);

}
