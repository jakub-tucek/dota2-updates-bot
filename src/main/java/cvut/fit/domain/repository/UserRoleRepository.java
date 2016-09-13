package cvut.fit.domain.repository;

import cvut.fit.domain.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for user roles
 * Created by Jakub Tuček on 29.8.2016.
 */
@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
}
