package cvut.fit.domain.repository;

import cvut.fit.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository
 * Created by Jakub Tuček on 29.8.2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
